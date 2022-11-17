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

.data
@aux@holajkds@Global@mergeSort dd  ? 
_a@Global@mergeSort@holajkds dd  ? 
@aux@mergeSort@Global dd  ? 
_a@Global dd  ? 
_d@Global dd  ? 
_b@Global dd  ? 
_a@Global@mergeSort dd  ? 
_c@Global dd  ? 
_3 dd 3
_b@Global@mergeSort@holajkds dd  ? 
@aux2 dd  ? 
_b@Global@mergeSort dd  ? 
@aux1 dd  ? 

.code
holajkds@Global@mergeSort:
MOV EAX , _b@Global@mergeSort@holajkds
MOV _a@Global@mergeSort@holajkds, EAX
MOV EAX, _3
MOV @aux@holajkds@Global@mergeSort, EAX
ret 

mergeSort@Global:
MOV EAX , _b@Global@mergeSort
MOV _a@Global@mergeSort, EAX
MOV EAX , _a@Global@mergeSort
MOV _a@Global@mergeSort@holajkds, EAX
MOV EAX , _b@Global@mergeSort
MOV _b@Global@mergeSort@holajkds, EAX
call holajkds@Global@mergeSort
FLD @aux@holajkds@Global@mergeSort
FST @aux2
MOV EAX, @aux2
MOV @aux@mergeSort@Global, EAX
ret 

main:
MOV EAX , _a@Global
MOV _a@Global@mergeSort, EAX
MOV EAX , _b@Global
MOV _b@Global@mergeSort, EAX
call mergeSort@Global
FLD @aux@mergeSort@Global
FST @aux1
MOV EAX , _c@Global
MOV _d@Global, EAX
end main