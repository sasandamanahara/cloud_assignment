#!/usr/bin/env python3
import sys

current_category = None
total = 0
count = 0

for line in sys.stdin:
    category, rating = line.strip().split("\t")
    rating = float(rating)

    if current_category == category:
        total += rating
        count += 1
    else:
        if current_category:
            avg = total / count
            print(f"{current_category}\t{avg:.2f}")
        current_category = category
        total = rating
        count = 1

if current_category:
    avg = total / count
    print(f"{current_category}\t{avg:.2f}")
