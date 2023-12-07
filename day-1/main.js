const fs = require("fs");

const INPUT_FILE = "input.txt";
const inputLines = fs.readFileSync(INPUT_FILE, "utf8").split("\n");

const SEMANTIC_NUMBERS = new Map([
    ["0", 0],
    ["1", 1],
    ["2", 2],
    ["3", 3],
    ["4", 4],
    ["5", 5],
    ["6", 6],
    ["7", 7],
    ["8", 8],
    ["9", 9],
    ["one", 1],
    ["two", 2],
    ["three", 3],
    ["four", 4],
    ["five", 5],
    ["six", 6],
    ["seven", 7],
    ["eight", 8],
    ["nine", 9],
]);

/**
 * @param {string} line
 * @param {boolean | undefined} reverse
 * @returns {number | null}
 */
function findNum(line, reverse = false) {
    let trackerIndex = reverse ? -1 : Infinity;
    let trackerNum = null;

    const indexOf = reverse
        ? line.lastIndexOf.bind(line)
        : line.indexOf.bind(line);

    for (const [key, val] of SEMANTIC_NUMBERS) {
        const idx = indexOf(key);
        if (idx === -1) continue;
        if (
            (reverse && idx > trackerIndex) ||
            (!reverse && idx < trackerIndex)
        ) {
            trackerIndex = idx;
            trackerNum = val;
        }
    }

    return trackerNum;
}

const calibrationSum = inputLines
    .map((line) => {
        const first = findNum(line);
        if (first === null) return null;
        const last = findNum(line, true);
        if (last === null) return null;
        return parseInt(`${first}${last}`);
    })
    .filter((n) => n !== null)
    .reduce((sum, n) => sum + n, 0);

console.log(calibrationSum);
