import json
import csv

stockx = {}
with open("stockx-final-28102020T1314.txt") as fp:
    raw_data = fp.read().split("\n")
    for i in range(0, len(raw_data) - 1, 3):
        name = raw_data[i]
        low = int(raw_data[i + 1].split("$")[-1].replace(",", ""))
        high = int(raw_data[i + 2].split("$")[-1].replace(",", ""))
        stockx[name] = {"low": low, "high": high}

result = {}
cleaned_goat = json.load(open("cleaned_goat.json"))
for shoe_name, goat_price in cleaned_goat.items():
    result[shoe_name] = {
        "goat": int(goat_price.split("$")[-1].replace(",", "")),
        "stockx_low": stockx.get(shoe_name, {}).get("low", -1),
        "stockx_high": stockx.get(shoe_name, {}).get("high", -1),
    }

with open("combined.json", "w+") as fp:
    json.dump(result, fp, indent=2)

with open("combined.csv", "w+") as fp:
    csv_writer = csv.writer(fp)
    headers = [
        "shoe_name",
        "goat_price",
        "stockx_low_price",
        "stockx_high_price",
        "low_profit",
        "high_profit",
    ]
    csv_writer.writerow(headers)
    back = []
    for k, v in result.items():
        high_profit = 0 if v["stockx_high"] == -1 else v["stockx_high"] - v["goat"]
        low_profit = 0 if v["stockx_low"] == -1 else v["stockx_low"] - v["goat"]
        row = [
            k,
            v["goat"],
            v["stockx_low"],
            v["stockx_high"],
            low_profit,
            high_profit,
        ]
        if v["stockx_low"] == -1 and v["stockx_high"] == -1:
            back.append(row)
        else:
            csv_writer.writerow(row)
    for row in back:
        csv_writer.writerow(row)
