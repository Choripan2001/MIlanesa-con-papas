package AreaAlumnos.RegistroDeNotas;

public class Nodo {

    private String dato;
    private Nodo izquierda, derecha;

    public Nodo(String dato) {
        this.dato = dato;
        this.izquierda = null;
        this.derecha = null;
    }
    
    public int getNota1(){
        int numSpace = 0;
        String nota1 = "";
        for(int i=0; i<dato.length(); i++){
            if(Character.isSpaceChar(dato.charAt(i))){
                numSpace++;
            }else{
                if(numSpace==0){
                    nota1 += dato.charAt(i);
                }
            }
        }
        int Nota1 = Integer.parseInt (nota1);
        return Nota1;
    }
    
    public int getNota2(){
        int numSpace = 0;
        String nota2 = "";
        for(int i=0; i<dato.length(); i++){
            if(Character.isSpaceChar(dato.charAt(i))){
                numSpace++;
            }else{
                if(numSpace==1){
                    nota2 += dato.charAt(i);
                }
            }
        }
        int Nota2 = Integer.parseInt (nota2);
        return Nota2;
    }
    
    public int getNota3(){
        int numSpace = 0;
        String nota3 = "";
        for(int i=0; i<dato.length(); i++){
            if(Character.isSpaceChar(dato.charAt(i))){
                numSpace++;
            }else{
                if(numSpace==2){
                    nota3 += dato.charAt(i);
                }
            }
        }
        int Nota3 = Integer.parseInt (nota3);
        return Nota3;
    }
    
    public int getNota4(){
        int numSpace = 0;
        String nota4 = "";
        for(int i=0; i<dato.length(); i++){
            if(Character.isSpaceChar(dato.charAt(i))){
                numSpace++;
            }else{
                if(numSpace==3){
                    nota4 += dato.charAt(i);
                }
            }
        }
        int Nota4 = Integer.parseInt (nota4);
        return Nota4;
    }
    
    public double getNotaF(){
        int numSpace = 0;
        String notaf = "";
        for(int i=0; i<dato.length(); i++){
            if(Character.isSpaceChar(dato.charAt(i))){
                numSpace++;
            }else{
                if(numSpace==4){
                    notaf += dato.charAt(i);
                }
            }
        }
        double notaF = Double.parseDouble (notaf);
        return notaF;
    }
    
    public String getCurso(){
        int numSpace = 0;
        String curso = "";
        for(int i=0; i<dato.length(); i++){
            if(Character.isSpaceChar(dato.charAt(i))){
                numSpace++;
            }else{
                if(numSpace==5){
                    curso += dato.charAt(i);
                }
            }
        }
        return curso;
    }
    
    public String getNombre(){
        int numSpace = 0;
        String nombre = "";
        for(int i=0; i<dato.length(); i++){
            if(Character.isSpaceChar(dato.charAt(i))){
                numSpace++;
            }else{
                if(numSpace==6){
                    nombre += dato.charAt(i);
                }
            }
        }
        return nombre;
    }
    
    public String getApellido(){
        int numSpace = 0;
        String apellido = "";
        for(int i=0; i<dato.length(); i++){
            if(Character.isSpaceChar(dato.charAt(i))){
                numSpace++;
            }else{
                if(numSpace==7){
                    apellido += dato.charAt(i);
                }
            }
        }
        return apellido;
    }
    
    public String getCodigo(){
        int numSpace = 0;
        String codigo = "";
        for(int i=0; i<dato.length(); i++){
            if(Character.isSpaceChar(dato.charAt(i))){
                numSpace++;
            }else{
                if(numSpace==8){
                    codigo += dato.charAt(i);
                }
            }
        }
        return codigo;
    }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }

    public Nodo getIzquierda() {
        return izquierda;
    }

    public void setIzquierda(Nodo izquierda) {
        this.izquierda = izquierda;
    }

    public Nodo getDerecha() {
        return derecha;
    }

    public void setDerecha(Nodo derecha) {
        this.derecha = derecha;
    }
    
    public void imprimirDato() {
        System.out.println(this.dato);
    }
}
