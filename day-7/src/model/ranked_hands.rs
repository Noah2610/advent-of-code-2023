use super::Hand;

pub struct RankedHands {
    pub hands: Vec<Hand>,
}

impl RankedHands {
    pub fn new(hands: Vec<Hand>) -> Self {
        Self { hands }
    }
}
