# Propositional Logic - Theorem Prover
## Theorem Prover for Propositional Logic using the truth table method for formulas in Conjuntive Normal Form.
The program indicates whether the input formula is unsatisfiable, satisfiable or tautology (also called logically valid).   

*This program is an implementation of the Exercise 2.5 in the book Introduction to Artificial Intelligence (Second Edition) by Wolfgang Ertel*

### Some definitions:
* **Interpretation** - A mapping I: E --> {t, f}, which assigns a truth value to every proposition variable, is called an interpretation. (Here E is the set of all propositional variables, also called literals)
* **Model of a formula** - Every interpretation that satisfies a formula is called a model of the formula.
* A formula is called -
  1. **Satisfiable** if it is true for at least one interpretation.
  2. **Unsatisfiable** if it is not true for any interpretation.
  3. **Logically Valid** if it is true for all interpretations (Tautology).
* Conjunctive Normal Formal (CNF) - A formula is in CNF if and only if it consists of a conjunction of clauses K1 ^ K2 ^ .... ^ Km and a clause Ki consists of a disjuntion (L1 v L2 v L3 v .... v Ln) of literals. Literal can be positive or negated. 

### Inputs:
Input formula should be in Conjuntive Normal Form. We consider that the formulas are entered as follows -- 
* First enter n, the number of clauses that conjunctively comprise the formula
* Now in the next n lines, enter literals for each clause as - x1x2x3....xm. Here each xi represents a literal. Negation of a literal is represented by adding a ! sign in front of it. Ex. !x means not of x.
* For Ex. To enter the formula (a v b) ^ (c v !d) ^ (a v c v d)
  1. First enter number of clauses n = 3
  2. Now enter the first clause as ab
  3. Second clause is entered as c!d
  4. Third clause is entered as acd

### Output:
The program prints the following information --
* Number of interpretations possible.
* The Truth Table formed.
* Whether the input formula is unsatisfiable, satisfiable or tautology with the number of true interpretations. 
