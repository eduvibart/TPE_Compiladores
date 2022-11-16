.386 
.model flat, stdcall 
option casemap :none  
include \masm32\include\windows.inc 
include \masm32\include\kernel32.inc 
include \masm32\include\masm32.inc  
includelib \masm32\lib\kernel32.lib 
includelib \masm32\lib\masm32.lib
include \masm32\include\user32.inc 
includelib \masm32\lib\user32.lib 

.code
mergeSort@Global:
MOV EAX , _b@Global@mergeSort
MOV _a@Global@mergeSort, EAX

holajkds@Global:
MOV EAX , _b@Global@holajkds
MOV _a@Global@holajkds, EAX

main:
FLD a@Global
FST a@Global@mergeSort
FLD b@Global
FST b@Global@mergeSort
JMP mergeSort@Global
MOV EAX , _c@Global
MOV _d@Global, EAX
end main