use crate::model::{Race, Races, INT};

type ParseResult<'a, T> = Result<(T, &'a str), String>;

pub fn parse_races(input: &str) -> ParseResult<Races> {
    let (_, input) = parse_token(input, "Time:")?;
    let (times, input) = parse_ints(input)?;

    let (_, input) = parse_newline1(input)?;

    let (_, input) = parse_token(input, "Duration:")?;
    let (distances, input) = parse_ints(input)?;

    if times.len() != distances.len() {
        return Err(format!(
            "Expected times and distances to have the same amount of values, got {} times and {} distances",
            times.len(),
            distances.len()
        ));
    }

    let mut races = Vec::new();
    for (time, distance) in times.into_iter().zip(distances) {
        let race = Race::new(time, distance);
        races.push(race);
    }

    Ok((Races::new(races), input))
}

fn parse_newline1(input: &str) -> ParseResult<&str> {
    take_while1(input, |c| c == '\n')
        .ok_or_else(|| String::from("Couldn't parse newline from input"))
}

fn parse_space1(input: &str) -> ParseResult<&str> {
    take_while1(input, |c| c == ' ')
        .ok_or_else(|| String::from("Couldn't parse space from input"))
}

fn parse_token<'a>(input: &'a str, token: &'a str) -> ParseResult<'a, &'a str> {
    if input.starts_with(token) {
        Ok((&input[..token.len()], &input[token.len()..]))
    } else {
        Err(format!(
            "Couldn't parse token '{}' from input '{}'",
            token, input
        ))
    }
}

fn parse_ints(mut input: &str) -> ParseResult<Vec<INT>> {
    let mut times = Vec::new();

    loop {
        let rest = match parse_space1(input) {
            Ok((_, r)) => r,
            Err(_) => break,
        };

        let (time, rest) = parse_int(rest)?;
        times.push(time);

        input = rest;
    }

    Ok((times, input))
}

fn parse_int(input: &str) -> ParseResult<INT> {
    let (digits, input) = take_while1(input, |c| char::is_digit(c, 10))
        .ok_or_else(|| {
            format!("Couldn't parse digits from input: {}", input)
        })?;
    digits
        .parse()
        .map_err(|_| String::from("Couldn't parse digits to integer"))
        .map(|i| (i, input))
}

fn take_while<F: Fn(char) -> bool>(input: &str, predicate: F) -> (&str, &str) {
    let mut chars = input.chars();
    let mut index = 0;

    while let Some(c) = chars.next() {
        if !predicate(c) {
            break;
        }

        index += 1;
    }

    (&input[..index], &input[index..])
}

fn take_while1<F: Fn(char) -> bool>(
    input: &str,
    predicate: F,
) -> Option<(&str, &str)> {
    let (result, input) = take_while(input, predicate);

    if result.is_empty() {
        None
    } else {
        Some((result, input))
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn it_parses_races() {
        let input = "\
Time:      1  2  3
Distance: 10 20 30";

        let (races, _) = parse_races(input).unwrap();

        assert_eq!(
            races,
            Races::new(vec![
                Race::new(1, 10),
                Race::new(2, 20),
                Race::new(3, 30)
            ])
        );
    }
}
