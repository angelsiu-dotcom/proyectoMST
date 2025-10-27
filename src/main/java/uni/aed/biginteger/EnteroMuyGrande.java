/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uni.aed.biginteger;


public class EnteroMuyGrande {
    
private static final char MENOS  = '-'; 
private Nodo head; 
private byte signo; 

    private EnteroMuyGrande(Nodo head) {
        this.head = head;
        this.signo = +1;
    }
    
    public EnteroMuyGrande suma(EnteroMuyGrande num){
        //return this.sumaPos(num); 
        
        EnteroMuyGrande L= new EnteroMuyGrande(this); 
        EnteroMuyGrande R = new EnteroMuyGrande(num); 
        
        if(L.esPositivo() && R.esPositivo()){
            return L.sumaPos(R);                
        }
        if (L.esPositivo() && R.esNegativo()){
            return L.restaPos(R.negativo()); 
        }
        if(L.esNegativo() && R.esPositivo()){
            return R.restaPos(L.negativo()); 
        }
        //Ambos negativos
        return L.negativo().sumaPos(R.negativo()).negativo(); 
    }
    
    public EnteroMuyGrande(EnteroMuyGrande num) {
        this.signo = num.signo; 
        this.head = new Nodo();
        Nodo p = head;
        Nodo q = num.head; 
        
        while(q != null ){
            p.next= new Nodo(q.data); 
            p = p.next; 
            q= q.next; 
        }
        
        this.head = this.head.next; 
        
    }
    private EnteroMuyGrande sumaPos(EnteroMuyGrande num){
        Nodo p, q, r, t; 
        p= this.head; 
        q= num.head; 
        t= new Nodo(); 
        r=t; 
        
        short acarreo= 0; 
        while(p != null && q != null) {
            short sum = (short) (acarreo +p.data + q.data);
            r.next= new Nodo(); 
            r= r.next; 
            
            r.data= (short) (sum % Nodo.VALOR_MAX);
            acarreo= (short) (sum/Nodo.VALOR_MAX); 
            p= p.next; 
            q = q.next; 
        }
        p = (p == null) ? q:p;
        
        while(p !=null){
            r.next= new Nodo(); 
            r = r.next; 
            
            r.data= (short) ((p.data + acarreo) % Nodo.VALOR_MAX);
            acarreo = (short) ((p.data + acarreo) / Nodo.VALOR_MAX);
            p=p.next; 
        }
        if(acarreo >0) {
            r.next = new Nodo((short) acarreo); 
        }
        return new EnteroMuyGrande(t.next); 
    }
    public EnteroMuyGrande(){
        this(0); 
    }
    
    public EnteroMuyGrande(long numero){
        this("" + numero); 
    }
    //Constructor de cadena
        public EnteroMuyGrande(String numero) {
        
        if (numero == null || numero.trim().isEmpty()) {
        throw new IllegalArgumentException("Cadena vacía o nula");
    }
        numero = numero.trim();
        signo = +1;
         char primerChar = numero.charAt(0);

    // Validar que el primer carácter sea dígito o signo '-'
    if (primerChar != MENOS && !Character.isDigit(primerChar)) {
        throw new IllegalArgumentException("Formato inválido: debe iniciar con un dígito o '-'.");
    }

    // Validar que el resto de la cadena sean solo dígitos
    for (int i = (primerChar == MENOS ? 1 : 0); i < numero.length(); i++) {
        if (!Character.isDigit(numero.charAt(i))) {
            throw new IllegalArgumentException("Formato inválido: contiene caracteres no numéricos.");
        }
    }
        if (numero.charAt(0) == MENOS) {
            signo = -1;
            numero = numero.substring(1); // quitar el signo '-'
        }

        numero = extraeCerosPrecedentes(numero);

        if (numero.equals("0")) {
            signo = +1; // el cero siempre positivo
        }
        head= new Nodo(); 
        Nodo cola= head; 
        String digitos; 
        
        while(!numero.equals("")){
            int loc = Math.max(numero.length() - Nodo.DIGITOS_MAX, 0); 
            digitos = numero.substring(loc); 
            
            numero= numero.substring(0, loc); 
            
            Nodo bloque= new Nodo(digitos); 
            
            cola.next= bloque; 
            cola= bloque;
        }
        head=head.next; 
        }
        
        
        //aString
        public String aString() {
            return aString('\0'); 
        }
        
        
        public String aString(char separador) {
    StringBuilder strBuf = new StringBuilder();
    Nodo p = head;

    // Construimos la cadena desde los bloques de nodos
    while (p != null) {
        String bloque = String.format("%0" + Nodo.DIGITOS_MAX + "d", p.data);
        strBuf.insert(0, bloque); // insertamos al inicio
        p = p.next;
    }

    // Insertamos separadores cada 3 dígitos desde el final
    if (separador != '\0') {
        for (int i = strBuf.length() - 3; i > 0; i -= 3) {
            strBuf.insert(i, separador);
        }
    }

    // Colocamos el signo si es negativo
    if (signo < 0) {
        strBuf.insert(0, '-');
    }

    return strBuf.toString();
}

        
        private static String extraeCerosPrecedentes(String str) {
            StringBuffer strBuf = new StringBuffer(str); 
            int length = strBuf.length(); 
            for(int i = 0; i < length; i++ ){
                if(strBuf.charAt(0)=='0'){
                    strBuf.deleteCharAt(0); 
                }
            }
            if(strBuf.length() == 0){
                    strBuf.append('0'); 
                }
                return strBuf.toString(); 
        }
        
        
        class Nodo{
            private static final short DIGITOS_MAX= 3; 
            private static final short VALOR_MAX = 1000; 
            private short data; 
            private Nodo next; 
            private Nodo(){
                this("0"); 
            }
            
            private Nodo(String str){
                this (Short.parseShort(str)); 
            }
            private Nodo(short val){
               data= val; 
               next= null; 
            }
        }
    
    

public int comparaA(EnteroMuyGrande num){
    EnteroMuyGrande L= this; 
    EnteroMuyGrande R = num; 
    
    if(L.esPositivo() && R.esNegativo()){
        return +1; 
    }
    if(L.esNegativo() && R.esPositivo()){
        return -1; 
    }
    
    String Lstr= L.toString(); 
    String Rstr = R.toString(); 
    
    int result; 
    
    int lengthL = Lstr.length(); 
    int lengthR= Rstr.length(); 
    
    if(lengthL == lengthR){
        result = Lstr.compareTo(Rstr); 
    }else{
    result= (lengthL< lengthR) ? -1 : +1; 
}

return L.signo * result;
} 

public EnteroMuyGrande resta(EnteroMuyGrande num){
    //return this.restaPos(num); 
    EnteroMuyGrande L = new EnteroMuyGrande(this);
    EnteroMuyGrande R = new EnteroMuyGrande(num); 
    if(L.esPositivo() && R.esPositivo()){
        return L.restaPos(R); 
    }
    
    if(L.esPositivo() && R.esNegativo()){
        return L.sumaPos(R.negativo()); 
    }
    
    if(L.esNegativo() && R.esPositivo()){
        return L.negativo().sumaPos(R).negativo(); 
    }
    return R.negativo().restaPos(L.negativo()); 
}
//multiplicacion simple 
public EnteroMuyGrande multiplica(EnteroMuyGrande num){
    EnteroMuyGrande L= new EnteroMuyGrande(0); 
    EnteroMuyGrande R= new EnteroMuyGrande(0); 
    
    while(R.comparaA(num)<0){
        L= L.suma(this); 
        R= R.suma(new EnteroMuyGrande(1)); 
        
    }
    
    if(this.signo*num.signo<0){
        L.signo= -1; 
    }
    return L; 
}
//eficiente 
public EnteroMuyGrande multiplicacion(EnteroMuyGrande otro) {
    EnteroMuyGrande resultado = new EnteroMuyGrande("0");
    
    Nodo nodoOtro= otro.head; 
    int ceros =0; 
    
    while(nodoOtro != null) {
        EnteroMuyGrande parcial = this.multiplicarPorUnDigito(nodoOtro.data); 
        
        parcial = parcial.agregarCeroFinal(ceros);
        
        resultado= resultado.suma(parcial); 
        
        nodoOtro = nodoOtro.next; 
        ceros++;
    }
    return resultado; 
}

private EnteroMuyGrande multiplicarPorUnDigito(short digito) {
    Nodo actual = this.head;
    int acarreo = 0;
    StringBuilder sb = new StringBuilder();

    while (actual != null) {
        int producto = actual.data * digito + acarreo;
        sb.append(producto % 10);  // último dígito
        acarreo = producto / 10;   // lo que sobra
        actual = actual.next;
    }

    if (acarreo > 0) {
        sb.append(acarreo);
    }

    return new EnteroMuyGrande(sb.reverse().toString());
}

private EnteroMuyGrande agregarCeroFinal(int cantidad) {
    String num = this.toString();
    for (int i = 0; i < cantidad; i++) {
        num += "0";
    }
    return new EnteroMuyGrande(num);
}



private EnteroMuyGrande extraeCerosPrecedentes(){
    String numStr = this.toString(); 
    String result  = extraeCerosPrecedentes(numStr); 
    
    if (result.equals("0")){
        return new EnteroMuyGrande(0); 
    }else if(result.length() < numStr.length()){
        return new EnteroMuyGrande(result); 
    }else{
        return this; 
    }
}

public EnteroMuyGrande divisionSimple(EnteroMuyGrande divisor) {
    if (divisor.comparaA(new EnteroMuyGrande(0)) == 0) {
        throw new ArithmeticException("División por cero");
    }

    EnteroMuyGrande dividendo = new EnteroMuyGrande(this);
    EnteroMuyGrande cociente = new EnteroMuyGrande(0);
    EnteroMuyGrande uno = new EnteroMuyGrande(1);

    // Determinar el signo del resultado
    boolean negativo = (this.esNegativo() ^ divisor.esNegativo());

    dividendo.signo = 1;
    divisor = new EnteroMuyGrande(divisor);
    divisor.signo = 1;

    while (dividendo.comparaA(divisor) >= 0) {
        dividendo = dividendo.resta(divisor);
        cociente = cociente.suma(uno);
    }

    if (negativo) cociente.negativo();

    return cociente;
}

public EnteroMuyGrande divisionLarga(EnteroMuyGrande divisor) {
    if (divisor.comparaA(new EnteroMuyGrande(0)) == 0) {
        throw new ArithmeticException("División por cero");
    }

    EnteroMuyGrande dividendo = new EnteroMuyGrande(this);
    EnteroMuyGrande divisorAbs = new EnteroMuyGrande(0);
    dividendo.signo = 1; 
    divisorAbs.signo= 1; 
    
    boolean negativo = (this.esNegativo() ^  divisor.esNegativo()); 
    
    dividendo.invertirLista();
    
    StringBuilder cocienteStr = new StringBuilder(); 
    EnteroMuyGrande residuo = new EnteroMuyGrande(0); 
    
    Nodo nodo= dividendo.head; 
    while(nodo != null){
        residuo= residuo.multiplica(new EnteroMuyGrande(Nodo.VALOR_MAX)); 
        residuo = residuo.suma(new EnteroMuyGrande(nodo.data));
        
        int count =0; 
        while(residuo.comparaA(divisorAbs) >= 0 ){
            residuo = residuo.resta(divisorAbs); 
            count++; 
        }
        cocienteStr.append(count); 
        nodo = nodo.next; 
    }
    
    EnteroMuyGrande cociente = new EnteroMuyGrande(cocienteStr.toString()); 
    
    if (negativo) cociente.negativo();

    return cociente;
}

public void invertirLista() {
    Nodo prev = null;
    Nodo actual = head;
    Nodo siguiente;

    while (actual != null) {
        siguiente = actual.next;  // guardamos el siguiente nodo
        actual.next = prev;       // invertimos el enlace
        prev = actual;            // avanzamos prev
        actual = siguiente;       // avanzamos actual
    }

    head = prev; // el último nodo procesado será la nueva cabeza
}


public void incr() {
    Nodo actual = head;
    int acarreo = 1; // porque queremos sumar 1

    while (actual != null && acarreo > 0) {
        int suma = actual.data + acarreo;
        actual.data = (short) (suma % Nodo.VALOR_MAX);
        acarreo = suma / Nodo.VALOR_MAX;

        if (actual.next == null && acarreo > 0) {
            // Si es el último nodo y queda acarreo, creamos un nuevo nodo
            actual.next = new Nodo((short) acarreo);
            acarreo = 0; // ya no hay más acarreo
        }

        actual = actual.next;
    }
}
 

public void decr() {
    if (head == null) return; // nada que hacer

    Nodo actual = head;
    int prestamo = 1; // porque queremos restar 1

    while (actual != null && prestamo > 0) {
        int resta = actual.data - prestamo;

        if (resta < 0) {
            actual.data = (short) (resta + Nodo.VALOR_MAX);
            prestamo = 1; // seguimos prestando al siguiente nodo
        } else {
            actual.data = (short) resta;
            prestamo = 0; // ya no hay préstamo
        }

        // Si es el último nodo y el valor queda en 0 y no es el único nodo
        if (actual.next == null && actual.data == 0 && head.next != null) {
            // Podríamos eliminar nodos innecesarios al final
            // (opcional, dependiendo de cómo quieras manejar ceros al inicio)
        }

        actual = actual.next;
    }
}

private boolean esPositivo(){
    return signo>0; 
}

private boolean esNegativo(){
    return signo<0; 
}

private EnteroMuyGrande negativo(){
    signo = (byte) - signo; 
    return this; 
}
//resta 
private EnteroMuyGrande restaPos(EnteroMuyGrande num){
    Nodo p, q, r, t; 
    boolean esNegativo = false; 
    
    if (this.comparaA(num) >= 0){
        p= this.head; 
        q= num.head; 
    }else{
        p= num.head; 
        q= this.head; 
        esNegativo= true; 
    }
    
    t= new Nodo(); 
    r = t; 
    short prestamo = 0; 
    short minuendo; 
    
    while(p != null && q != null){
        r.next = new Nodo(); 
        r = r.next; 
        minuendo = (short) (p.data - prestamo);
        
        if(minuendo < q.data){
            r.data = (short) (Nodo.VALOR_MAX + minuendo - q.data); 
            prestamo=1; 
        } else{
            r.data= (short) (minuendo -q.data); 
            prestamo=0; 
        }
        p= p.next; 
        q= q.next; 
    }
    p=(p==null) ? q:p;  
    
    while (p!= null){
        r.next = new Nodo(); 
        r= r.next; 
        
        r.data = (short) (p.data - prestamo); 
        
        if (r.data < 0){
            r.data += Nodo.VALOR_MAX; 
            prestamo = 1; 
        } else {
            prestamo=0; 
        }
        p= p.next; 
    }
    EnteroMuyGrande result = new EnteroMuyGrande(t.next);
    result = result.extraeCerosPrecedentes();
    if(esNegativo) result.negativo();
    
    return result; 
} 
        
}


