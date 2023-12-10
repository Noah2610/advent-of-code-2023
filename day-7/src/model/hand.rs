use super::Card;
use super::Kind;

pub const CARD_COUNT: usize = 5;

pub type Cards = [Card; CARD_COUNT];
pub type Bid = u32;

#[derive(Clone, PartialEq, Eq)]
pub struct Hand {
    pub cards: Cards,
    pub kind: Kind,
    pub bid: Bid,
}

impl Hand {
    pub fn new(cards: Cards, kind: Kind, bid: Bid) -> Self {
        Self { cards, kind, bid }
    }
}

impl std::fmt::Display for Hand {
    fn fmt(&self, f: &mut std::fmt::Formatter<'_>) -> std::fmt::Result {
        let cards = self
            .cards
            .iter()
            .map(|card| card.to_string())
            .collect::<String>();
        write!(f, "{} {} {}", cards, self.kind, self.bid)
    }
}

impl std::fmt::Debug for Hand {
    fn fmt(&self, f: &mut std::fmt::Formatter<'_>) -> std::fmt::Result {
        std::fmt::Display::fmt(self, f)
    }
}
