use std::fs::File;
use std::io::Read;

const INPUT_FILE: &str = "./input.txt";

const EMPTY: char = '.';

#[derive(Debug)]
struct Grid {
    rows: Vec<GridRow>,
}

#[derive(Debug)]
struct GridRow {
    cells: Vec<GridCell>,
}

#[derive(Debug)]
struct GridCell {
    y_start: usize,
    y_end: usize,
    x_start: usize,
    x_end: usize,
    content: GridCellContent,
}

#[derive(Debug)]
enum GridCellContent {
    Num { num: u32 },
    Symbol { symbol: char },
    Empty,
}

impl Grid {
    fn new() -> Self {
        Self { rows: Vec::new() }
    }

    fn append_cell(&mut self, cell: GridCell) {
        if self.rows.is_empty() {
            self.new_row();
        }
        self.rows
            .last_mut()
            .expect("Expected row")
            .append_cell(cell);
    }

    fn new_row(&mut self) {
        self.rows.push(GridRow::new());
    }

    fn symbols(&self) -> Vec<&GridCell> {
        let mut cells = Vec::new();

        for row in &self.rows {
            for cell in &row.cells {
                if cell.is_symbol() {
                    cells.push(cell);
                }
            }
        }

        cells
    }

    fn neighbors_of(&self, target_cell: &GridCell) -> Vec<&GridCell> {
        let mut neighbors = Vec::new();

        for row in &self.rows {
            for cell in &row.cells {
                if Self::is_neighbor(target_cell, cell) {
                    neighbors.push(cell);
                }
            }
        }

        neighbors
    }

    fn is_neighbor(one: &GridCell, two: &GridCell) -> bool {
        let y_range = one.y_start.checked_sub(1).unwrap_or(one.y_start)
            ..=one.y_end.checked_add(1).unwrap_or(one.y_end);
        let x_range = one.x_start.checked_sub(1).unwrap_or(one.x_start)
            ..=one.x_end.checked_add(1).unwrap_or(one.x_end);
        (two.y_start..=two.y_end).any(|y| y_range.contains(&y))
            && (two.x_start..=two.x_end).any(|x| x_range.contains(&x))
    }
}

impl GridRow {
    fn new() -> Self {
        Self { cells: Vec::new() }
    }

    fn append_cell(&mut self, cell: GridCell) {
        self.cells.push(cell);
    }
}

impl GridCell {
    fn num(
        x_start: usize,
        y_start: usize,
        x_end: usize,
        y_end: usize,
        num: u32,
    ) -> Self {
        GridCell {
            x_start,
            y_start,
            x_end,
            y_end,
            content: GridCellContent::Num { num },
        }
    }

    fn symbol(x_start: usize, y_start: usize, symbol: char) -> Self {
        GridCell {
            x_start,
            y_start,
            x_end: x_start,
            y_end: y_start,
            content: GridCellContent::Symbol { symbol },
        }
    }

    fn empty(x_start: usize, y_start: usize) -> Self {
        GridCell {
            x_start,
            y_start,
            x_end: x_start,
            y_end: y_start,
            content: GridCellContent::Empty,
        }
    }

    fn is_num(&self) -> bool {
        self.content.is_num()
    }

    fn is_symbol(&self) -> bool {
        self.content.is_symbol()
    }

    fn is_empty(&self) -> bool {
        self.content.is_empty()
    }
}

impl GridCellContent {
    fn is_num(&self) -> bool {
        if let GridCellContent::Num { .. } = self {
            true
        } else {
            false
        }
    }

    fn is_symbol(&self) -> bool {
        if let GridCellContent::Symbol { .. } = self {
            true
        } else {
            false
        }
    }

    fn is_empty(&self) -> bool {
        if let GridCellContent::Empty = self {
            true
        } else {
            false
        }
    }
}

impl From<String> for Grid {
    fn from(s: String) -> Self {
        let mut grid = Grid::new();

        let lines = s.lines();
        lines.enumerate().for_each(|(y, line)| {
            let mut num_start = None;
            let mut num_s = String::new();

            for (x, c) in line.chars().enumerate() {
                if c.is_digit(10) {
                    num_s.push(c);
                    if num_start.is_none() {
                        num_start = Some(x);
                    }
                } else {
                    if !num_s.is_empty() {
                        let num = num_s.parse::<u32>().expect(&format!(
                            "Expected to parse number \"{}\"",
                            &num_s
                        ));

                        grid.append_cell(GridCell::num(
                            num_start.expect("Expected num_start"),
                            y,
                            x - 1,
                            y,
                            num,
                        ));

                        num_s = String::new();
                        num_start = None;
                    }

                    if c == EMPTY {
                        grid.append_cell(GridCell::empty(x, y));
                    } else {
                        grid.append_cell(GridCell::symbol(x, y, c));
                    }
                }
            }

            if !num_s.is_empty() {
                let num = num_s.parse::<u32>().expect(&format!(
                    "Expected to parse number \"{}\"",
                    &num_s
                ));

                let num_start = num_start.expect("Expected num_start");

                grid.append_cell(GridCell::num(
                    num_start,
                    y,
                    num_start + num_s.len() - 1,
                    y,
                    num,
                ));
            }

            grid.new_row();
        });

        grid
    }
}

fn read_input() -> String {
    let mut file = File::open(INPUT_FILE).expect("Could not open file");
    let mut out = String::new();
    file.read_to_string(&mut out).expect("Could not read file");
    return out;
}

fn main() {
    let input = read_input();
    let grid = Grid::from(input);

    let mut sum = 0;

    for symbol in grid.symbols() {
        for neighbor in grid.neighbors_of(&symbol) {
            if let GridCellContent::Num { num } = &neighbor.content {
                sum += num;
            }
        }
    }

    println!("{}", sum);
}
