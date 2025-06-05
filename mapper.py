#!/usr/bin/env python3
import sys
import csv

for line in csv.reader(sys.stdin):
    try:
        category = line[4]
        rating = float(line[3])
        if category and rating:
            print(f"{category}\t{rating}")
    except:
        continue
