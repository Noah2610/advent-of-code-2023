use crate::model::Cards;
use crate::model::Kind;
use crate::model::Label;
use crate::model::CARD_COUNT;
use std::collections::HashMap;

type Counts = HashMap<Label, u8>;

pub fn compute_kind(cards: &Cards) -> Option<Kind> {
    let mut counts = count_labels(cards);
    handle_jokers(&mut counts);
    return compute_kind_from_counts(&counts);
}

fn count_labels(cards: &Cards) -> Counts {
    let mut counts = Counts::new();

    for card in cards {
        counts.insert(
            card.label.clone(),
            counts.get(&card.label).unwrap_or(&0) + 1,
        );
    }

    return counts;
}

fn compute_kind_from_counts(counts: &HashMap<Label, u8>) -> Option<Kind> {
    let mut has_two = false;
    let mut has_three = false;
    let mut has_one = false;

    for count in counts.values() {
        match count {
            5 => return Some(Kind::FiveOfAKind),
            4 => return Some(Kind::FourOfAKind),
            3 if has_two => return Some(Kind::FullHouse),
            3 => has_three = true,
            2 if has_three => return Some(Kind::FullHouse),
            2 if has_two => return Some(Kind::TwoPair),
            2 => has_two = true,
            1 => has_one = true,
            _ => (),
        }
    }

    return if has_three {
        Some(Kind::ThreeOfAKind)
    } else if has_two {
        Some(Kind::OnePair)
    } else if has_one {
        Some(Kind::HighCard)
    } else {
        None
    };
}

fn handle_jokers(counts: &mut Counts) {
    if let Some(mut jokers) = counts.remove(&Label::J) {
        while jokers > 0 {
            if let Some(highest_label) = find_label_to_incr(&counts) {
                counts.insert(
                    highest_label.clone(),
                    counts.get(highest_label).unwrap_or(&0) + 1,
                );
                jokers -= 1;
            } else {
                if jokers > 0 {
                    counts.insert(Label::J, jokers);
                }
                break;
            }
        }
    }
}

fn find_label_to_incr(counts: &Counts) -> Option<&Label> {
    counts
        .into_iter()
        .filter(|(label, count)| {
            !label.is_joker() && **count < (CARD_COUNT as u8)
        })
        .max_by_key(|(_, count)| *count)
        .map(|(label, _)| label)
}
