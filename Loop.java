List<String> days = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", 
				   "Friday", "Saturday", "Sunday"); //Set, Queue also work
days.forEach(System.out::println);

// series function call
optimize opt[5] = { optimize1, optimize2, optimize3, optimize4, optimize5 };
for (int i = 0; i < opt.length(); i++) {
    opt[i](&v, dest);
}
