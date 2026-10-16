static int total(int[] pages) {
    int sum = pages[0] + pages[1];
    return sum;
}

void main() {
    int[] pages = {5, 7};
    int sum = total(pages);
    IO.println(sum);
    IO.println(sum / 2.0);
}
