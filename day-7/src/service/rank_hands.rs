use crate::model::Hand;
use crate::model::Kind;
use crate::model::Label;
use crate::model::RankedHands;

pub fn rank_hands(hands: Vec<Hand>) -> RankedHands {
    let ranks_map = hands
        .iter()
        .map(|hand| (compute_hand_strength(hand), hand.clone()))
        .collect::<std::collections::BTreeMap<u64, Hand>>();
    return RankedHands::new(ranks_map.into_values().collect());
}

fn compute_hand_strength(hand: &Hand) -> u64 {
    const BASE: u8 = Label::BASE + Kind::BASE;

    let kind_strength = hand.kind.strength() as u32;
    let label_strength = hand
        .cards
        .iter()
        .rev()
        .enumerate()
        .map(|(i, card)| {
            (Label::BASE as u64).pow(i as u32) * card.label.strength() as u64
        })
        .sum::<u64>();

    return (BASE as u64).pow(kind_strength) * label_strength;
}

#[cfg(test)]
mod tests {
    use super::*;
    use crate::model::Card;

    #[test]
    fn it_ranks_hands() {
        let hands_ranked = vec![
            Hand::new(
                [
                    Card::new(Label::Two),
                    Card::new(Label::Two),
                    Card::new(Label::J),
                    Card::new(Label::Q),
                    Card::new(Label::K),
                ],
                Kind::OnePair,
                0,
            ),
            Hand::new(
                [
                    Card::new(Label::Three),
                    Card::new(Label::Three),
                    Card::new(Label::J),
                    Card::new(Label::Q),
                    Card::new(Label::K),
                ],
                Kind::OnePair,
                0,
            ),
            Hand::new(
                [
                    Card::new(Label::Two),
                    Card::new(Label::Two),
                    Card::new(Label::J),
                    Card::new(Label::Three),
                    Card::new(Label::Three),
                ],
                Kind::TwoPair,
                0,
            ),
            Hand::new(
                [
                    Card::new(Label::Two),
                    Card::new(Label::Two),
                    Card::new(Label::Two),
                    Card::new(Label::Q),
                    Card::new(Label::K),
                ],
                Kind::ThreeOfAKind,
                0,
            ),
            Hand::new(
                [
                    Card::new(Label::Four),
                    Card::new(Label::Two),
                    Card::new(Label::Two),
                    Card::new(Label::Two),
                    Card::new(Label::Three),
                ],
                Kind::ThreeOfAKind,
                0,
            ),
            Hand::new(
                [
                    Card::new(Label::Two),
                    Card::new(Label::Two),
                    Card::new(Label::Two),
                    Card::new(Label::Three),
                    Card::new(Label::Three),
                ],
                Kind::FullHouse,
                0,
            ),
            Hand::new(
                [
                    Card::new(Label::Three),
                    Card::new(Label::Two),
                    Card::new(Label::Two),
                    Card::new(Label::Two),
                    Card::new(Label::Two),
                ],
                Kind::FourOfAKind,
                0,
            ),
            Hand::new(
                [
                    Card::new(Label::Two),
                    Card::new(Label::Two),
                    Card::new(Label::Two),
                    Card::new(Label::Two),
                    Card::new(Label::Two),
                ],
                Kind::FiveOfAKind,
                0,
            ),
            Hand::new(
                [
                    Card::new(Label::A),
                    Card::new(Label::A),
                    Card::new(Label::A),
                    Card::new(Label::A),
                    Card::new(Label::A),
                ],
                Kind::FiveOfAKind,
                0,
            ),
        ];

        let hands = vec![
            hands_ranked.get(5).unwrap().clone(),
            hands_ranked.get(3).unwrap().clone(),
            hands_ranked.get(0).unwrap().clone(),
            hands_ranked.get(2).unwrap().clone(),
            hands_ranked.get(1).unwrap().clone(),
            hands_ranked.get(4).unwrap().clone(),
            hands_ranked.get(8).unwrap().clone(),
            hands_ranked.get(6).unwrap().clone(),
            hands_ranked.get(7).unwrap().clone(),
        ];

        let ranked_hands = rank_hands(hands);

        assert_eq!(ranked_hands.hands, hands_ranked);
    }
}
