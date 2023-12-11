use super::compute_kind;
use crate::model::Bid;
use crate::model::Card;
use crate::model::Cards;
use crate::model::Hand;
use crate::model::CARD_COUNT;

pub fn parse_hands(mut input: &str) -> Option<(Vec<Hand>, &str)> {
    let mut hands = Vec::new();
    while let Some((hand, rest)) = parse_hand(input) {
        hands.push(hand);
        if let Some((_, rest)) = parse_whitespace1(rest) {
            input = rest;
        } else {
            input = rest;
            break;
        }
    }

    return Some((hands, input));
}

pub fn parse_hand(input: &str) -> Option<(Hand, &str)> {
    let (cards, input) = parse_cards(input)?;
    let (_, input) = parse_whitespace1(input)?;
    let (bid, input) = parse_bid(input)?;

    let kind = compute_kind(&cards)?;

    return Some((Hand::new(cards, kind, bid), input));
}

fn parse_cards(mut input: &str) -> Option<(Cards, &str)> {
    let mut cards = Vec::new();
    for _ in 0..CARD_COUNT {
        let (card, rest) = parse_card(input)?;
        cards.push(card);
        input = rest;
    }

    let cards: Cards = cards.try_into().ok()?;

    return Some((cards, input));
}

fn parse_card(input: &str) -> Option<(Card, &str)> {
    let card = Card::try_from(input.chars().nth(0)?).ok()?;
    return Some((card, &input[1..]));
}

fn parse_bid(input: &str) -> Option<(Bid, &str)> {
    let (numS, rest) = take_while1(input, |c| c.is_digit(10))?;
    let num = numS.parse::<Bid>().ok()?;
    return Some((num, rest));
}

fn parse_whitespace(input: &str) -> Option<(&str, &str)> {
    take_while(input, |c| c.is_whitespace())
}

fn parse_whitespace1(input: &str) -> Option<(&str, &str)> {
    take_while1(input, |c| c.is_whitespace())
}

fn take_while<F: Fn(char) -> bool>(
    input: &str,
    predicate: F,
) -> Option<(&str, &str)> {
    let mut chars = input.chars();
    let mut index = 0;
    while let Some(c) = chars.next() {
        if !predicate(c) {
            break;
        }
        index += 1;
    }

    return Some((&input[..index], &input[index..]));
}

fn take_while1<F: Fn(char) -> bool>(
    input: &str,
    predicate: F,
) -> Option<(&str, &str)> {
    let mut chars = input.chars();
    let mut index = 0;
    while let Some(c) = chars.next() {
        if !predicate(c) {
            break;
        }
        index += 1;
    }

    if index == 0 {
        return None;
    }

    return Some((&input[..index], &input[index..]));
}

#[cfg(test)]
mod tests {
    use super::*;
    use crate::model::{Kind, Label};

    #[test]
    fn it_parses_hand() {
        let (hand, _) = parse_hand("32T3K 765").unwrap();

        assert_eq!(
            hand.cards,
            [
                Card::new(Label::Three),
                Card::new(Label::Two),
                Card::new(Label::T),
                Card::new(Label::Three),
                Card::new(Label::K),
            ]
        );
        assert_eq!(hand.kind, Kind::OnePair);
        assert_eq!(hand.bid, 765);
    }
}
