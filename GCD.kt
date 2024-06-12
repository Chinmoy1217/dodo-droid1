fun main() {
   var input1 = 18
   var input2 = 24
   var myResult = 1

   println("The input values are $input1 and $input2")
   var i = 1
   while (i <= input1 && i <= input2) {
      if (input1 % i == 0 && input2 % i == 0)
         myResult = i
      ++i
   }
println("The result is $myResult")
}