package Salon

class Salon(var i : String) {
    /*Atributos*/
    var _id : String = i
    var _temp : Int = 35
    var _reservas : List[(Int,String)]= List()
    var _habilitado : Boolean = true
    var _enUso : Boolean = false
    var _luz : Boolean = false

    /*Métodos*/
    def cambiarTemp(n : Int) : Unit = _temp = n

    def habilitar() : Unit = _habilitado = true
    def desHabilitar() : Unit = _habilitado = false

    def abrir() : Unit = _enUso = true
    def cerrar() : Unit = _enUso = false

    def nuevaReserva(hora : Int, usu : String) : Unit ={
        if(hora >= 7 && hora <= 19){
            var aux = false
            _reservas.foreach{
                x => if(x._1 == hora) aux = true
            }
            
            if(aux == false){
                    var tmp = (hora, usu)
                    _reservas = tmp :: _reservas
                    _reservas = _reservas.sorted
            }
        }
    }

    def prenderLuces() : Unit = {
        _luz = true
    }
    def apagarLuces() : Unit = {
        _luz = false
    }

    /*Setters*/
    def reservas_(n:List[(Int,String)]):Unit={
        _reservas = n
    }
    /*Getters*/
    def getId = _id
    def getTemp = _temp
    def getReservas = _reservas
    def getHabilitado = _habilitado
    def getEnUso = _enUso
    def getLuz = _luz
}