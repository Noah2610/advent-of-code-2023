mod model;
mod service;

use std::fs::File;
use std::io::Read;

const INPUT_FILE: &str = "input-dev.txt";

fn main() {}

fn read_input() -> String {
    let mut file = File::open(INPUT_FILE).expect("Could not open file");
    let mut out = String::new();
    file.read_to_string(&mut out).expect("Could not read file");
    return out;
}
