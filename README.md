# TextSearch

assumption :

 Per thread bufferbyte should fit into available physical memory.
 
 Here are the steps to run:
 
 git clone https://github.com/ombits/TextSearch.git
 cd TextSearch/src
 javac -cp . com/src/moonfrog/java/*.java
 java -cp . com/src/moonfrog/java/ReadFileWithFixedSizeBuffer <file_name> <search_string> <no_of_threads>
