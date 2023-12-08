import fs from "fs";

const INPUT_FILE = "input.txt";
const inputLines = fs.readFileSync(INPUT_FILE, "utf8").split("\n");

interface Game {
    cards: Cards;
    lastCardId: number;
}

type Cards = Map<number, Card[]>;

interface Card {
    id: number;
    winning: CardNumbers;
    having: CardNumbers;
}

type CardNumbers = Set<number>;

function toInt(s: string): number {
    const n = parseInt(s);
    if (Number.isNaN(n)) {
        throw new Error(`Couldn't parse "${s}" to integer`);
    }
    return n;
}

function parseCard(line: string): Card {
    const [cardLine, numbersLine] = line.split(":");
    const id = toInt(cardLine.replace(/\s+/, " ").split(" ")[1]);
    const [winningLine, havingLine] = numbersLine.split("|");

    const winning = parseNumbers(winningLine);
    const having = parseNumbers(havingLine);

    return {
        id,
        winning,
        having,
    };
}

function parseNumbers(line: string): CardNumbers {
    return new Set(line.trim().replace(/\s+/g, " ").split(" ").map(toInt));
}

function calcMatches(card: Card): number {
    let matches = 0;

    for (const have of card.having) {
        if (card.winning.has(have)) {
            matches++;
        }
    }

    return matches;
}

function countCards(cards: Cards): number {
    return [...cards.values()].reduce(
        (total, cards) => total + cards.length,
        0,
    );
}

function main() {
    const cards: Cards = new Map<number, Card[]>();
    let lastCardId = 0;

    for (const line of inputLines) {
        if (line.trim().length === 0) continue;
        const card = parseCard(line);
        cards.set(card.id, [card]);
        lastCardId = Math.max(lastCardId, card.id);
    }

    const game: Game = {
        cards,
        lastCardId,
    };

    let id = 1;

    while (id <= game.lastCardId) {
        const cards = game.cards.get(id);
        if (cards === undefined) {
            throw new Error(`Expected card(s) for id ${id}`);
        }

        for (const card of cards) {
            const matches = calcMatches(card);
            for (let i = id + 1; i <= id + matches; i++) {
                const cardCopy = game.cards.get(i)?.[0];
                if (cardCopy === undefined) {
                    throw new Error(`Expected card for id ${i} for copy`);
                }
                game.cards.get(i)?.push({ ...cardCopy });
            }
        }

        id++;
    }

    const totalCards = countCards(game.cards);

    console.log(totalCards);
}

main();
