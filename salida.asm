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
errorMensFun db "El camino tomado por la funcion no tiene retorno.", 0 
errorMensDivisionPorCero db "No se puede dividir por cero.", 0 
errorMensProductoEnteros db "Se produjo un overflow en el producto de enteros.", 0 
errorMensRecursionMutua db "Se produjo un llamado recursivo mutuo.", 0 
error db "Error de ejecucion!!!", 0 
@aux@mergeSort@Global dd  ? 
_a@Global dd  ? 
@aux@prueba@Global dd  ? 
_b@Global dd  ? 
_a@Global@mergeSort dd  ? 
_0 dd 0
_c@Global dd  ? 
_1 dd 1
_2 dd 2
@aux@prueba1@Global dd  ? 
@aux3 dd  ? 
@aux2 dd  ? 
_b@Global@mergeSort dd  ? 
@aux1 dd  ? 

.code
errorFun: 
invoke MessageBox, NULL, addr errorMensFun, addr error, MB_OK 
invoke ExitProcess, 1 
errorDivisionPorCero: 
invoke MessageBox, NULL, addr errorMensDivisionPorCero, addr error, MB_OK 
invoke ExitProcess, 1 
errorProductoEnteros: 
invoke MessageBox, NULL, addr errorMensProductoEnteros, addr error, MB_OK 
invoke ExitProcess, 1 
errorRecursionMutua: 
invoke MessageBox, NULL, addr errorMensRecursionMutua, addr error, MB_OK 
invoke ExitProcess, 1 
prueba@Global:
MOV EAX, _0
MOV @aux@prueba@Global, EAX
ret 
JMP errorFun
mergeSort@Global:
MOV EAX , _b@Global@mergeSort
MOV _a@Global@mergeSort, EAX
call prueba@Global
MOV EAX, @aux@prueba@Global
MOV @aux1, EAX
MOV EAX, _b@Global@mergeSort
MOV @aux@mergeSort@Global, EAX
ret 
JMP errorFun
prueba1@Global:
MOV EAX, _0
MOV @aux@prueba1@Global, EAX
ret 
MOV EAX , _a@Global
MOV _a@Global@mergeSort, EAX
MOV EAX , _b@Global
MOV _b@Global@mergeSort, EAX
CMP mergeSort@Global, mergeSort@Global
JE errorRecursionMutua
call mergeSort@Global
MOV EAX, @aux@mergeSort@Global
MOV @aux2, EAX
JMP errorFun
main:
MOV EAX , _1
MOV _a@Global, EAX
MOV EAX , _2
MOV _b@Global, EAX
MOV EAX , _a@Global
MOV _a@Global@mergeSort, EAX
MOV EAX , _b@Global
MOV _b@Global@mergeSort, EAX
call mergeSort@Global
MOV EAX, @aux@mergeSort@Global
MOV @aux3, EAX
MOV EAX , @aux3
MOV _c@Global, EAX
end main