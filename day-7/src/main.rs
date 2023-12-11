mod model;
mod service;

use std::fs::File;
use std::io::Read;

const INPUT_FILE: &str = concat!(env!("CARGO_MANIFEST_DIR"), "/input.txt");

fn main() -> Result<(), String> {
    let input = read_input();

    let (hands, rest) =
        service::parse_hands(&input).ok_or("Could not parse input as hands")?;
    if !rest.trim().is_empty() {
        return Err(format!("Expected to parse full input: {}", rest));
    }

    let ranked = service::rank_hands(hands);

    let mut total_winnings: u32 = 0;

    for (i, hand) in ranked.get_hands().iter().enumerate() {
        let rank = (i + 1) as u32;
        let winnings = hand.bid * rank;
        total_winnings += winnings;
    }

    println!("{}", total_winnings);

    Ok(())
}

fn read_input() -> String {
    let mut file = File::open(INPUT_FILE).expect("Could not open file");
    let mut out = String::new();
    file.read_to_string(&mut out).expect("Could not read file");
    return out;
}
