# SistemaReservas
## INFORMACIÓN IMPORTANTE
El menú de la interfaz realiza todas las acciones que está indincadas
sin embargo hay algunas de estas acciones que a partir de la consola no son visibles 
para el usuario, puesto que modifican a los objetos internamente.
Para mostrar el cambio de estos objetos abajo en la sección prueba se encuentra un driver 
que ilustra el cambio en los objetos con los metodos disponibles
para verlo:

1. Comentar el loop "while(!exit){}"
2. Copiar la seccion prueba que se encuentra en este documento más abajo, en el archivo "sistema.scala".
3. Correr

Como no puedo automatizar la verificacion de la hora, creé metodos que cambien a 
todos los salones cuando se le pasa una hora determinada, estas son las opciones
11,12,y 13.
se puede ver el cambio con los pasos de arriba.


/*--------------------------PRUEBA------------------------------------*/
        crearSalon("1")
        crearSalon("2")
        crearSalon("3")

        Salones.foreach{
            x => println("ID = "+x.getId)
        }

        desHabilitarSalon("1")
        Salones.foreach{
            x => if(x.getId == "1"){
                 println("Habilitado = "+x.getHabilitado)
            }
        }
        habilitarSalon("1")
        Salones.foreach{
            x => if(x.getId == "1"){
                println("Habilitado = "+x.getHabilitado)
            }
        }

        cerrarSalon("1")
        Salones.foreach{
            x => if(x.getId == "1"){
                println("En uso = "+x.getEnUso)
            }
        }
        abrirSalon("1")
        Salones.foreach{
            x => if(x.getId == "1"){
                println("En uso = "+x.getEnUso)
            }
        }

        cambiarTemp("1", 12)
        Salones.foreach{
            x => if(x.getId == "1"){
                println("temperatura = "+x.getTemp)
            }
        }

        hacerReserva("1",usuarioAct.getCedula,8)
        Salones.foreach{
            x => if(x.getId == "1"){
                println("reservas Activas: ")
                x.getReservas.foreach{
                    y => println(y._1)
                }
            }
        }
        hacerReserva("1",usuarioAct.getCedula,7)
        Salones.foreach{
            x => if(x.getId == "1"){
                println("reservas Activas: ")
                x.getReservas.foreach{
                    y => println(y._1)
                }
            }
        }
        hacerReserva("1",usuarioAct.getCedula,9)
        Salones.foreach{
            x => if(x.getId == "1"){
                println("reservas Activas: ")
                x.getReservas.foreach{
                    y => println(y._1)
                }
            }
        }

        cancelarReserva("1",8)
        Salones.foreach{
            x => if(x.getId == "1"){
                println("Nuevas reservas: ")
                x.getReservas.foreach{
                    y => println(y._1)
                }
            }
        }

        
        Salones.foreach{
            x => if(x.getId == "1"){
                println("Luces: ")
                println(x.getLuz)
            }
        }
        verificarLuz(6,55)
        Salones.foreach{
            x => if(x.getId == "1"){
                println("Luces: ")
                println(x.getLuz)
            }
        }
        verificarLuz(10,11)
        Salones.foreach{
            x => if(x.getId == "1"){
                println("Luces: ")
                println(x.getLuz)
            }
        }
        verificarLuz(10,10)
        Salones.foreach{
            x => if(x.getId == "1"){
                println("Luces: ")
                println(x.getLuz)
            }
        }

        Salones.foreach{
            x => if(x.getId == "1"){
                println("cerradura: ")
                println(x.getEnUso)
            }
        }
        verificarCerradura(7,45)
        Salones.foreach{
            x => if(x.getId == "1"){
                println("cerradura: ")
                println(x.getEnUso)
            }
        }
        verificarCerradura(10,00)
        Salones.foreach{
            x => if(x.getId == "1"){
                println("cerradura: ")
                println(x.getEnUso)
            }
        }

        Salones.foreach{
            x => if(x.getId == "1"){
                println("temperatura: ")
                println(x.getTemp)
            }
        }
        verificacarTemp(6,50)
        Salones.foreach{
            x => if(x.getId == "1"){
                println("temperatura: ")
                println(x.getTemp)
            }
        }
        verificacarTemp(8,5)
        Salones.foreach{
            x => if(x.getId == "1"){
                println("temperatura: ")
                println(x.getTemp)
            }
        }
		

		disponibilidad("1")
		hacerReserva("1",usuarioAct.getCedula,19)
		disponibilidad("1")
