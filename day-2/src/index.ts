import fs from "node:fs";

const INPUT_FILE = "./input.txt";
const inputLines = fs.readFileSync(INPUT_FILE, "utf8").split("\n");

const MAX_RED = 12;
const MAX_GREEN = 13;
const MAX_BLUE = 14;

type CubeColor = "red" | "green" | "blue";
type CubeColorCount = {
    [key in CubeColor]: number;
};

const CUBE_COLORS = new Set(["red", "green", "blue"]);

function newCubeColorCount(): CubeColorCount {
    return {
        red: 0,
        green: 0,
        blue: 0,
    };
}

function isCubeColor(s: string): s is CubeColor {
    return CUBE_COLORS.has(s);
}

function toInt(s: string): number {
    const n = parseInt(s);
    if (Number.isNaN(n)) {
        throw new Error(`Couldn't parse "${s}" to int`);
    }
    return n;
}

function toColor(s: string): CubeColor {
    if (isCubeColor(s)) {
        return s;
    }
    throw new Error(`Couldn't parse "${s}" to color`);
}

function countCubesOfGame(gameLine: string): [number, CubeColorCount] {
    const [gameS, roundsLineS] = gameLine.split(":");
    const gameId = toInt(gameS.split(" ")[1]);

    let cubeColorCount = newCubeColorCount();

    const roundLines = roundsLineS.split(";");

    for (const roundLine of roundLines) {
        const roundCubeColorCount = newCubeColorCount();

        const colorLines = roundLine.split(",").map((s) => s.trim());
        for (const colorLine of colorLines) {
            const [countS, colorS] = colorLine.split(" ").map((s) => s.trim());
            const count = toInt(countS);
            const color = toColor(colorS);
            roundCubeColorCount[color] += count;
        }

        cubeColorCount = maxCubeColorCount(cubeColorCount, roundCubeColorCount);
    }

    return [gameId, cubeColorCount];
}

function maxCubeColorCount(
    one: CubeColorCount,
    two: CubeColorCount,
): CubeColorCount {
    return (Object.keys(one) as CubeColor[]).reduce(
        (acc, color) => ({ ...acc, [color]: Math.max(one[color], two[color]) }),
        {} as Partial<CubeColorCount>,
    ) as CubeColorCount;
}

function main() {
    const gamesWithCount = new Map<number, CubeColorCount>();

    for (const line of inputLines) {
        if (line.trim().length === 0) continue;
        const [gameId, count] = countCubesOfGame(line);
        gamesWithCount.set(gameId, count);
    }

    let totalPower = 0;
    for (const [gameId, count] of gamesWithCount) {
        const power = count.red * count.green * count.blue;
        totalPower += power;
    }

    console.log(totalPower);

    // let gameIdSum = 0;
    // for (const [gameId, count] of gamesWithCount) {
    //     if (
    //         count.red <= MAX_RED &&
    //         count.green <= MAX_GREEN &&
    //         count.blue <= MAX_BLUE
    //     ) {
    //         gameIdSum += gameId;
    //     }
    // }

    // console.log(gameIdSum);
}

main();

export {};
