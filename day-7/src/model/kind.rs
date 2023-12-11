use std::collections::HashMap;

#[derive(Ord, PartialEq, Eq, Hash, Debug, Clone)]
pub enum Kind {
    FiveOfAKind,
    FourOfAKind,
    FullHouse,
    ThreeOfAKind,
    TwoPair,
    OnePair,
    HighCard,
}

impl Kind {
    pub const BASE: u8 = 7;

    pub fn strength(&self) -> u8 {
        use Kind::*;

        match self {
            FiveOfAKind => 7,
            FourOfAKind => 6,
            FullHouse => 5,
            ThreeOfAKind => 4,
            TwoPair => 3,
            OnePair => 2,
            HighCard => 1,
        }
    }
}

impl std::cmp::PartialOrd for Kind {
    fn partial_cmp(&self, other: &Self) -> Option<std::cmp::Ordering> {
        self.strength().partial_cmp(&other.strength())
    }
}

impl std::fmt::Display for Kind {
    fn fmt(&self, f: &mut std::fmt::Formatter<'_>) -> std::fmt::Result {
        use Kind::*;

        write!(
            f,
            "{}",
            match self {
                FiveOfAKind => "Five of a kind",
                FourOfAKind => "Four of a kind",
                FullHouse => "Full house",
                ThreeOfAKind => "Three of a kind",
                TwoPair => "Two pair",
                OnePair => "One pair",
                HighCard => "High card",
            }
        )
    }
}
