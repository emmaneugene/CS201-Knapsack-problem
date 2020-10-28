result = {}
with open("final.txt") as fp:
    data = fp.read().split("\n")
    for i in range(0, len(data)):
        line = data[i]
        if len(line) > 6 and "$" not in line and not result.get(line, False):
            result[line] = data[i + 1].split(" ")[-1]

import json
with open("cleaned_goat.json", "w+") as fp:
    json.dump(result, fp, indent=2)