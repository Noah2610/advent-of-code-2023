use super::Label;

#[derive(Debug, Clone, Eq, PartialEq)]
pub struct Card {
    pub label: Label,
}

impl Card {
    pub fn new(label: Label) -> Self {
        Self { label }
    }
}

impl TryFrom<char> for Card {
    type Error = ();

    fn try_from(c: char) -> Result<Self, Self::Error> {
        Label::try_from(c).map(|label| Self::new(label))
    }
}

impl std::fmt::Display for Card {
    fn fmt(&self, f: &mut std::fmt::Formatter<'_>) -> std::fmt::Result {
        write!(f, "{}", self.label)
    }
}
