const fs = require("fs");

const inputLines = fs.readFileSync("./input.txt", "utf8").split("\n");

const calibrationSum = inputLines
    .map((line) => {
        const chars = line.split("");
        const first = chars.find((c) => !isNaN(c));
        if (first === undefined) return null;
        const last = chars.reverse().find((c) => !isNaN(c));
        if (last === undefined) return null;
        return parseInt(first + last);
    })
    .filter((n) => n !== null)
    .reduce((sum, n) => sum + n, 0);

console.log(calibrationSum);
