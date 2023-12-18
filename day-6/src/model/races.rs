pub type INT = u32;

#[derive(Debug, Eq, PartialEq)]
pub struct Races {
    pub races: Vec<Race>,
}

impl Races {
    pub fn new(races: Vec<Race>) -> Self {
        Self { races }
    }
}

#[derive(Debug, Eq, PartialEq)]
pub struct Race {
    pub time: INT,
    pub distance: INT,
}

impl Race {
    pub fn new(time: INT, distance: INT) -> Self {
        Self { time, distance }
    }
}
