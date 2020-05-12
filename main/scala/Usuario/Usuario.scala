package Usuario

class Usuario(var nom : String,var ced : String,var adm : Boolean){
    /*Atributos*/
    var _nombre : String = nom
    var _cedula : String = ced
    var _esAdmin : Boolean = adm

    /*Setters*/
    def nombre_(n:String):Unit= _nombre = n
    def cedula_(n:String):Unit= _cedula = n
    
    /*Getters*/
    def getAdmin = _esAdmin
    def getNombre = _nombre
    def getCedula = _cedula

    /*Métodos*/
    
}