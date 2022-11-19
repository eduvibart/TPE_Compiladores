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
_a@Global dd  ? 
_b@Global dd  ? 
_c@Global dd  ? 
_1 dd 1
_2 dd 2
_3 dd 3
@aux3 dd  ? 
@aux2 dd  ? 
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
main:
MOV EAX , _1
MOV _a@Global, EAX
MOV EAX , _2
MOV _b@Global, EAX
etiqueta:
MOV EAX , 0
MOV _a@Global, EAX
label_2:
MOV EAX, _a@Global
CMP EAX, _3
JGE label_1
MOV EAX, _a@Global
ADD EAX, _1
MOV @aux2, EAX
MOV EAX , @aux2
MOV _a@Global, EAX
JMP etiqueta
label_3:
MOV EAX, _a@Global
ADD EAX, _1
MOV @aux3, EAX
MOV EAX , @aux3
MOV _a@Global, EAX
JMP label_2
label_1:
end main