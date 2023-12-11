#[derive(Ord, PartialEq, Eq, Hash, Debug, Clone)]
pub enum Label {
    A,
    K,
    Q,
    J,
    T,
    Nine,
    Eight,
    Seven,
    Six,
    Five,
    Four,
    Three,
    Two,
}

impl Label {
    pub const BASE: u8 = 13;

    pub fn strength(&self) -> u8 {
        use Label::*;
        match self {
            A => 13,
            K => 12,
            Q => 11,
            J => 1,
            T => 10,
            Nine => 9,
            Eight => 8,
            Seven => 7,
            Six => 6,
            Five => 5,
            Four => 4,
            Three => 3,
            Two => 2,
        }
    }

    pub fn is_joker(&self) -> bool {
        match self {
            Label::J => true,
            _ => false,
        }
    }
}

impl std::cmp::PartialOrd for Label {
    fn partial_cmp(&self, other: &Self) -> Option<std::cmp::Ordering> {
        self.strength().partial_cmp(&other.strength())
    }
}

impl TryFrom<char> for Label {
    type Error = ();

    fn try_from(c: char) -> Result<Self, Self::Error> {
        use Label::*;

        match c {
            'A' => Ok(A),
            'K' => Ok(K),
            'Q' => Ok(Q),
            'J' => Ok(J),
            'T' => Ok(T),
            '9' => Ok(Nine),
            '8' => Ok(Eight),
            '7' => Ok(Seven),
            '6' => Ok(Six),
            '5' => Ok(Five),
            '4' => Ok(Four),
            '3' => Ok(Three),
            '2' => Ok(Two),
            _ => Err(()),
        }
    }
}

impl std::fmt::Display for Label {
    fn fmt(&self, f: &mut std::fmt::Formatter<'_>) -> std::fmt::Result {
        use Label::*;

        write!(
            f,
            "{}",
            match self {
                A => 'A'.to_string(),
                K => 'K'.to_string(),
                Q => 'Q'.to_string(),
                J => 'J'.to_string(),
                T => 'T'.to_string(),
                Nine => '9'.to_string(),
                Eight => '8'.to_string(),
                Seven => '7'.to_string(),
                Six => '6'.to_string(),
                Five => '5'.to_string(),
                Four => '4'.to_string(),
                Three => '3'.to_string(),
                Two => '2'.to_string(),
            }
        )
    }
}
