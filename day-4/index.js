const fs = require("fs");

const INPUT_FILE = "input.txt";
const inputLines = fs.readFileSync(INPUT_FILE, "utf8").split("\n");

/**
 * @param {string} s
 * @returns {number}
 * @throws {Error}
 */
function toInt(s) {
    const n = parseInt(s);
    if (Number.isNaN(n)) {
        throw new Error(`Couldn't parse "${s}" to integer`);
    }
    return n;
}

/**
 * @param {string} line
 * @returns {number[]}
 */
function parseNumbers(line) {
    return line.trim().replace(/\s+/g, " ").split(" ").map(toInt);
}

/**
 * @param {string} line
 * @returns {number}
 */
function calcPoints(line) {
    const [_, s] = line.split(":");
    const [winningLine, havingLine] = s.split("|");

    const winning = parseNumbers(winningLine);
    const having = parseNumbers(havingLine);

    let points = 0;

    for (const have of having) {
        if (winning.includes(have)) {
            if (points === 0) {
                points = 1;
                continue;
            }

            points *= 2;
        }
    }

    return points;
}

function main() {
    let points = 0;

    for (const line of inputLines) {
        if (line.trim().length === 0) continue;
        points += calcPoints(line);
    }

    console.log(points);
}

main();
