import json
import sys

if len(sys.argv) < 3:
    sys.exit(1)

path_from = sys.argv[1]
path_to = sys.argv[2]

with open(path_from) as file_from:
    with open(path_to,'w') as file_to:
        file_to.write(json.dumps([s.replace('\n','') for s in file_from.readlines()]))
