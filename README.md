Genetic algorithm for the game 2048

TO RUN:
1. Navigate to `2048` directory
2. `javac *.java`
3. `java Game2048`

This will run 10 iterations of the following with a new initial population each time (it can take up to 10 mins):

    20 generations

    - Mutation Probability: 100%
    - Population Size: 65
    - Tournament Size: 5
    - Tree depth: 3-7

These settings can be changed at the top of `Breeder2048`.

Results:
Initial and final population are printed each run. The best individual of each generation is printed along with its score and max tile. The best individual of each run, and of all 10 runs is also printed.

<img src="2048.png" width="50%" height="50%"/>
