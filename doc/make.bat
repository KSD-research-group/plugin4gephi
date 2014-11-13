doxygen Doxyfile >doxygen_stdout.txt 2>doxygen_stderror.txt
cd latex
call make.bat  >..\latex_stdout.txt 2>..\latex_stderror.txt
copy refman.pdf ..\..\RefMan.pdf
