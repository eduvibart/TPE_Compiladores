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
@tagAnt dd ? 
@aux7 dd  ? 
@aux6 dd  ? 
@aux5 dd  ? 
@aux4 dd  ? 
_a@Global dd  ? 
_b@Global dd  ? 
_1 dd 1
_c@Global dd  ? 
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
MOV EAX , _3
MOV _c@Global, EAX
MOV EAX , 0
MOV _a@Global, EAX
et1:
label_2:
MOV EAX, _a@Global
CMP EAX, _b@Global
JGE label_1
et2:
label_4:
MOV EAX, _a@Global
CMP EAX, _b@Global
JGE label_5
MOV EAX , 0
MOV _c@Global, EAX
label_8:
MOV EAX, _c@Global
CMP EAX, _b@Global
JGE label_7
MOV EAX, _a@Global
ADD EAX, _1
MOV @aux4, EAX
MOV EAX , @aux4
MOV _a@Global, EAX
JMP et1
label_9:
MOV EAX, _c@Global
ADD EAX, _1
MOV @aux5, EAX
MOV EAX , @aux5
MOV _c@Global, EAX
JMP label_8
label_7:
label_6:
MOV EAX, _b@Global
ADD EAX, _1
MOV @aux6, EAX
MOV EAX , @aux6
MOV _b@Global, EAX
JMP label_4
label_5:
label_3:
MOV EAX, _a@Global
ADD EAX, _1
MOV @aux7, EAX
MOV EAX , @aux7
MOV _a@Global, EAX
JMP label_2
label_1:
invoke ExitProcess, 0 
end main