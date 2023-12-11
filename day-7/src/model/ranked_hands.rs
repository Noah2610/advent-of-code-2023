use super::Hand;

pub struct RankedHands {
    hands_with_strengths: Vec<(Hand, u64)>,
}

impl RankedHands {
    pub fn new(hands_with_strengths: Vec<(Hand, u64)>) -> Self {
        Self {
            hands_with_strengths,
        }
    }

    pub fn get_hands(&self) -> Vec<&Hand> {
        self.hands_with_strengths
            .iter()
            .map(|(hand, _)| hand)
            .collect()
    }

    pub fn get_hands_with_strengths(&self) -> Vec<&(Hand, u64)> {
        self.hands_with_strengths.iter().collect()
    }
}
