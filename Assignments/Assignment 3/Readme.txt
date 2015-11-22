I have created DecisionTreeTest and a DecisionStumpTest classes to perform tests on each of the datatypes.

DecisionTreeTest produces the output for answers B and C.

DecisionTreeTest take the full path to a file on the system to perform the tests. The file must be of the same format as iris.data.txt, with the same number of columns of the same data type.

I have added a method called getAvgExternalProbibality to DecisionTree which determines the avg max probability of all external decision stumps. This is used to calculate the statistics in part C.