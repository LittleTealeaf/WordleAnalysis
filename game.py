import json
import random
from turtle import clone
# game emulatopr

def load_words() -> list[str]:
    with open('./res/words.json') as file:
        return json.load(file)

WORD_LIST = load_words()

DEFAULT = 0
WRONG = 1
PARTIAL = 2
CORRECT = 3

class Game:
    def __init__(self) -> None:
        self.guesses = []
        self.word = random.choice(WORD_LIST)
        self.valid_words = WORD_LIST.copy()
        pass

    def guess(self,word: str):
        if len(word) != len(self.word) or word not in WORD_LIST:
            return None

        stack = [c for c in self.word]
        values = [WRONG] * len(word)
        for i in range(len(self.word)):
            if word[i] == self.word[i]:
                values[i] = CORRECT
                stack.remove(word[i])
        for i in range(len(self.word)):
            if word[i] in stack:
                values[i] = PARTIAL
                stack.remove(word[i])

        self.guesses.append(word)

        # filter valid words
        filtered = []
        for vword in self.valid_words:
            filter = True
            stack = [c for c in vword]
            for i in range(len(vword)):
                # if vword[i] == word[i] and values[i] != CORRECT:
                #     filter = False
                # HELP
                #     break
                if values[i] == WRONG and word[i] in vword:
                    filter = False
                    break
                if vword[i] != word[i] ^ values[i] == CORRECT:
                    filter = False
                    break
                else:
                    stack.remove(vword[i])
                if values[i] == PARTIAL:
                    if word[i] in stack:
                        stack.remove(word[i])
                    else:
                        filter = False
                        break

            if filter:
                filtered.append(vword)




        self.valid_words = filtered

        return values
