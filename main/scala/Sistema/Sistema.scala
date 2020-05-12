package Sistema

import scala.io.StdIn._
import Usuario._
import Salon._

object Sistema extends App{
    
    var exit : Boolean = false

    var usuRegular = new Usuario("PaulNormal","67890",false)
    var usuAdmin = new Usuario("PaulAdmin","12345",true)
    var usuarioAct = usuAdmin
    
    var Salones : List[Salon] = List()

    def crearSalon(i : String) : Unit = {
        
        if(usuarioAct.getAdmin == true){
            var band = false
            Salones.foreach{
                x => if(x.getId == i){
                    band = true
                }
            }
            
            if(!band){
                var aux = new Salon(i)
                Salones = aux :: Salones
            }
        }
        else{
            println("\nNo tiene permisos para realizar esta acción.\n")
            Thread.sleep(1000)
        }
    }

    def hacerReserva(id:String, ced:String, hor:Int) : Unit = {
        Salones.foreach{
            x => if(x.getId == id && x.getHabilitado == true){
                if(!isIn(x.getReservas,hor)){
                    x.nuevaReserva(hor,ced)
                }
                else{
                    println("\nLa reserva NO está disponible.\n")
                    Thread.sleep(1000)
                }
            }
        }
    }

    def cancelarReserva(id:String, hora:Int) : Unit = {
        Salones.foreach{
            x => if(x.getId == id){
                if(isIn(x.getReservas,hora)){
                    x.getReservas.foreach{
                        y => if(y._1 == hora){
                            if(usuarioAct.getCedula == y._2 || usuarioAct.getAdmin == true){
                                /*var tmp = x.getReservas
                                tmp.filter{case(v,_) => v == hora}
                                x.reservas_(tmp)*/
                                x._reservas = x._reservas.filter{case(v,_) => v != hora}
                                println("\nLa reserva fué cancelada exitosamente.\n")
                            }
                            else{
                                println("\nUsted NO tiene permiso para realizar esta acción.\n")
                                Thread.sleep(1000)
                            }
                        }
                    }
                }
                else{
                    println("\nLa reserva no existe\n")
                    Thread.sleep(1000)
                }
            }
        }
    }

    def habilitarSalon(id : String) : Unit = {
        if(usuarioAct.getAdmin == true){
            Salones.foreach{
                x => if(x.getId == id){
                  x.habilitar()
                }
            }
        }
        else{
            println("\nUsted NO tiene permiso para realizar esta acción.\n")
            Thread.sleep(1000)
        }
    }

    def desHabilitarSalon(id : String) : Unit = {
        if(usuarioAct.getAdmin == true){
            Salones.foreach{
                x => if(x.getId == id){
                    x.desHabilitar()
                }
            }
        }
        else{
            println("\nUsted NO tiene permiso para realizar esta acción.\n")
            Thread.sleep(1000)
        }
    }

    def abrirSalon(id : String) : Unit = {
        Salones.foreach{
            x => if(x.getId == id){
                if(x.getHabilitado == true){
                    x.abrir()
                    println("\nEl salon con ID: "+x.getId+" abrió correctamente.")
                    Thread.sleep(1000)
                }
            }
        }
    }

    def cerrarSalon(id : String) : Unit = {
        Salones.foreach{
            x => if(x.getId == id){
                if(x.getHabilitado == true){
                    x.cerrar()
                    println("\nEl salón con ID: "+x.getId+" cerró correctamente.\n")
                    Thread.sleep(1000)
                }
            }
        }
    }

    def disponibilidad(id : String) : Unit = {
        
        //var flag = false
        println("\nReservas Disponibles: \n")
        Salones.foreach{
            x => if(x.getId == id){
                //flag = true
                for(w <- 7 to 19){
                    if(!isIn(x.getReservas,w)){
                        println(w)
                    }
                }
            }
        }
        /*if(!flag){
            println("\nEl id de salón NO existe.\n")
            Thread.sleep(1000)
        }*/
        Thread.sleep(2500)
    }

    def cambiarTemp(id:String, tmp: Int) : Unit = {
        if(usuarioAct.getAdmin == true){
            Salones.foreach{
                x => if(x.getId == id){
                    x.cambiarTemp(tmp)
                }
            }
        }
        else{println("Usted no es administrador para realizar esta acción.")
                Thread.sleep(1000)}
    }
    
    def isIn(lst:List[(Int,String)],n:Int):Boolean = {
        var aux = false
        lst.foreach{
            x => if(x._1 == n) aux = true
        }
        return aux
    }
    
    def salonExists(lst:List[Salon],id:String) : Boolean = {
        var flag = false
        lst.foreach{
            x => if(x.getId == id){
                flag = true
            }
        }
        return flag
    }

    /*Métodos de verificación y control porque no se puede automatizar*/
    def verificarLuz(hora:Int, min:Int) : Unit = {
        if(min == 55){
            Salones.foreach{
                x => if(isIn(x.getReservas,hora+1)){
                        x.prenderLuces()
                    }
                    else x.apagarLuces()
            }
        }

        if(min == 10){
            Salones.foreach{
                x => if(!isIn(x.getReservas,hora)){
                        x.apagarLuces()
                    }
            }
        }
    }

    def verificarCerradura(hora:Int, min:Int) : Unit = {
        if(min == 45){
            Salones.foreach{
                x => if(isIn(x.getReservas,hora+1)){
                        abrirSalon(x.getId)
                    }
                    else cerrarSalon(x.getId)
            }
        }

        if(min == 00){
            Salones.foreach{
                x => if(!isIn(x.getReservas,hora)){
                        cerrarSalon(x.getId)
                    }
            }
        }
    }

    def verificacarTemp(hora:Int, min:Int) : Unit = {
        if(min == 50){
            Salones.foreach{
                x => if(isIn(x.getReservas,hora+1)){
                        cambiarTemp(x.getId,23)
                    }
                    else (x.getId,40)
            }
        }

        if(min == 5){
            Salones.foreach{
                x => if(!isIn(x.getReservas,hora)){
                        cambiarTemp(x.getId,40)
                    }
            }
        }
    }

    def menu(): Unit ={
        /*MENÚ*/ 
        println("------------Bienvenido------------")
        println("--------Sistema Reservas----------")
        println("\n")
        println("Elija su opción: ")
        println("1. Cambiar de usuario")
        println("2. Hacer reserva")
        println("3. Cancelar reserva")
        println("4. Abrir Salón")
        println("5. Cerrar Salón")
        println("6. Disponibilidad de Salón")
        println("\n")
        println("-----Solo para administradores.-----")
        println("7. Crear Salón")
        println("8. Habilitar Salón")
        println("9. Deshabilitar Salon")
        println("10. Cambiar Temperatura salón")
        println("\n")
        println("-----opciones de verificación-----")
        println("11. Verificar Luz")
        println("12. Verificar Cerradura")
        println("13. Verificar Temperatura")
        println("14. SALIR")
    }
    
    while(!exit){
       var Opcion : Int = 0
        var aux : Int = 0
        var aux1 : Int = 0
        var aux2: Int = 0
        var aux3 : Int = 0
        var aux4 : String =""
        var aux5 : String =""

        menu()
        Opcion = try{
            scala.io.StdIn.readInt()
        }catch{
            case ex: NumberFormatException => println("\nNúmero inválido.\n")
                                                Thread.sleep(1000)
                                                0
        }
        
        if(Opcion == 1){
            /*Cambiar Usuario*/
            if(usuarioAct == usuAdmin) {
                usuarioAct = usuRegular
                println("\nCambió a usuario regular\n")
                Thread.sleep(1000)
            }
            else {
                usuarioAct = usuAdmin
                println("\nCambió a usuario Administrador\n")
                Thread.sleep(1000)
            }
        }
        if(Opcion == 2){
            /*Hacer reserva*/
            var flag = true
            println("\nIntroduzca el id del salón: ")
            aux4 = scala.io.StdIn.readLine()
            println("Introduzca la hora de reserva: ")
            aux = try{
                scala.io.StdIn.readInt()
            } catch{
                case ex: NumberFormatException => println("\nNúmero inválido.\n")
                                                    flag = false
                                                    Thread.sleep(1000)
                                                    0
            }
            
            if(flag == true){
                if(salonExists(Salones,aux4)){
                    hacerReserva(aux4,usuarioAct.getCedula,aux)
                }
                else{
                    println("\nEl id de salón NO existe.\n")
                    Thread.sleep(1000)
                }
            }
        }
        if(Opcion == 3){
            /*Cancelar Reserva*/
            var flag = true
            println("\nIntroduzca el id del salón: ")
            aux4 = scala.io.StdIn.readLine()
            println("Introduzca la hora de reserva: ")
            aux = try{
                scala.io.StdIn.readInt()
                } catch{
                    case ex: NumberFormatException => println("\nNúmero inválido.\n")
                                                        Thread.sleep(1000)
                                                        flag = false
                                                        0 
                }

            if(flag == true){
                if(salonExists(Salones,aux4)){
                    cancelarReserva(aux4,aux)
                }
                else{
                    println("\nEl ID de salón No existe\n")
                    Thread.sleep(1000)
                }
            }
        }
        if(Opcion == 4){
            /*Abrir Salón*/
            println("\nIntroduzca el id del salón: ")
            aux4 = scala.io.StdIn.readLine()
            if(salonExists(Salones,aux4)){
                abrirSalon(aux4)
            }
            else{
                println("\nEl ID de salón NO existe.\n")
                Thread.sleep(1000)
            }
        }
        if(Opcion == 5){
            /*Cerrar Salón*/
            println("\nIntroduzca el id del salón: ")
            aux4 = scala.io.StdIn.readLine()

            if(salonExists(Salones,aux4)){
                cerrarSalon(aux4)
            }
            else{
                println("\nEl ID de salón NO existe.\n")
                Thread.sleep(1000)
            }
        }
        if(Opcion == 6){
            /*Disponibilidad Salon*/
            println("Introduzca el id del salón a consultar: ")
            aux4 = scala.io.StdIn.readLine()

            if(salonExists(Salones,aux4)){
                disponibilidad(aux4)
            }
            else{
                println("\nEl ID de salón NO existe.\n")
                Thread.sleep(1000)
            }

        }
        if(Opcion == 7){
            /*Crear Salón*/
            println("\nIntroduzca el id del salón: ")
            aux4 = scala.io.StdIn.readLine()

            crearSalon(aux4)
        }
        if(Opcion == 8){
            /*Habilitar Salón*/
            println("\nIntroduzca el id del salón: ")
            aux4 = scala.io.StdIn.readLine()

            if(salonExists(Salones,aux4)){
                habilitarSalon(aux4)
            }
            else{
                println("\nEl ID de salón NO existe.\n")
                Thread.sleep(1000)
            }
        }
        if(Opcion == 9){
            /*Deshabilitar Salon*/
            println("\nIntroduzca el id del salón: ")
            aux4 = scala.io.StdIn.readLine()

            if(salonExists(Salones,aux4)){
                desHabilitarSalon(aux4)
            }
            else{
                println("\nEl ID de salón NO existe.\n")
                Thread.sleep(1000)
            }
        }
        if(Opcion == 10){
            /*Cambiar Temperatura salón*/
            var flag = true
            println("\nIntroduzca el id del salón: ")
            aux4 = scala.io.StdIn.readLine()
            println("a que temperatura desea el salón?: ")
            aux = try{
                scala.io.StdIn.readInt()
                } catch{
                    case ex: NumberFormatException => println("\nNúmero inválido.\n")
                                                        Thread.sleep(1000)
                                                        flag = false
                                                        0
                }

            if(flag == true){
                if(salonExists(Salones,aux4)){
                    cambiarTemp(aux4,aux)
                }
                else{
                    println("\nEl ID de salón NO existe.\n")
                    Thread.sleep(1000)
                }
            }
        }
        if(Opcion == 11){
            /*Verificar Luz*/
            var flag = true
            println("\nIntroduzca la hora y los minutos\nA hacer la verificación:\n")
            println("Hora: ")
            aux = try{
                scala.io.StdIn.readInt()
            } catch{ 
                case ex: NumberFormatException => println("\nNúmero inválido.\n")
                                                        Thread.sleep(1000)
                                                        flag = false
                                                        0
            }
            println("Minutos: ")
            aux1 = try{
                scala.io.StdIn.readInt()
            } catch{
                case ex: NumberFormatException => println("\nNúmero inválido.\n")
                                                        Thread.sleep(1000)
                                                        flag = false
                                                        0
            }

            if(flag == true){verificarLuz(aux,aux1)}
            else{
                println("\nLa acción no se realizó.\n")
                Thread.sleep(1000)    
            }
        }
        if(Opcion == 12){
            /*Verificar Cerradura*/
            var flag = true
            println("\nIntroduzca la hora y los minutos\nA hacer la verificación:\n")
            println("Hora: ")
            aux = try{
                scala.io.StdIn.readInt()
            } catch{
                case ex: NumberFormatException => println("\nNúmero inválido.\n")
                                                        Thread.sleep(1000)
                                                        flag = false
                                                        0
            }
            println("Minutos: ")
            aux1 = try{
                scala.io.StdIn.readInt()
            } catch{
                case ex: NumberFormatException => println("\nNúmero inválido.\n")
                                                        Thread.sleep(1000)
                                                        flag = false
                                                        0
            }

            if(flag == true){verificarCerradura(aux,aux1)}
            else{
                println("\nLa acción no se realizó.\n")
                Thread.sleep(1000)    
            }
        }
        if(Opcion == 13){
            /*Verificar Temperatura*/
            var flag = true
            println("\nIntroduzca la hora y los minutos\nA hacer la verificación:\n")
            println("Hora: ")
            aux = try{
                scala.io.StdIn.readInt()
            } catch{
                case ex: NumberFormatException => println("\nNúmero inválido.\n")
                                                        Thread.sleep(1000)
                                                        flag = false
                                                        0
            }
            println("Minutos: ")
            aux1 = try{
                scala.io.StdIn.readInt()
            } catch{
                case ex: NumberFormatException => println("\nNúmero inválido.\n")
                                                        Thread.sleep(1000)
                                                        flag = false
                                                        0
            }

            if(flag == true){verificacarTemp(aux,aux1)}
            else{
                println("\nLa acción no se realizó.\n")
                Thread.sleep(1000)    
            }
        }
        if(Opcion == 14){
            /*Salir*/
            println("Gracias por usar el sistema.")
            exit = true        
        }
    }
}