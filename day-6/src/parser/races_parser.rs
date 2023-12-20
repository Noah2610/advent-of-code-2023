use crate::parser::Races;
use parser_lib::prelude::{Parser, TokensIterator};

pub struct RacesParser;

impl Parser for RacesParser {
    type Parsed = Races;

    type Iter<'a> = TokensIterator<'a>;

    fn parse<'a>(&self, tokens: &mut Self::Iter<'a>) -> Option<Self::Parsed> {
        todo!()
    }
}
