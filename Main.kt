import java.util.*
import kotlin.collections.ArrayList

var ocupadas = Array(10) { Array(10) {0} }

//Muestra el contenido del tablero donde guardaremos los datos del array
fun mostartablero(j : Jugador) {

    val tab = j.getTablero()

    print("   ") //Imprime los numeros del 1 al 10 antes de la matriz
    for (i in 1..10)
    {
        print("$i  ")
    }

    print("\n")
    for (v in 0..9) //Itera por la matriz y indica el estado de cada posicion
    {
        /*
            🌊 - Agua
            🚢 - Tu barco
            💣 - Tocado
            🔥 - Hundido
        */

        print("${Char(65 + (v))} ")

        for (h in 0..9)
        {
            if(tab[v][h] == 0)
            {
                System.out.format("%3s%s","\uD83C\uDF0A"," ")
                //print("🌊  ")
            }
            else if(tab[v][h] == 1)
            {
                System.out.format("%3s%s","\uD83D\uDEA2"," ")
                //print("🚢   ")
            }
            else if(tab[v][h] == 2)
            {
                System.out.format("%3s%s","\uD83D\uDCA3"," ")
            }
            else if(tab[v][h] == 3)
            {
                System.out.format("%3s%s","\uD83D\uDD25"," ")
            }
            //System.out.format("%-3s",tablero[v][h])
            //print(tablero[v][h].toString() + "  ")
        }
        println()
    }
}

//Obtiene un numero a traves de un caracter
fun letraANum(str : Char) : Int
{
    //Lista de caracteres ordenados por key y value para evitar usar if-else o when.
    val valores = mapOf('A' to 0, 'B' to 1, 'C' to 2, 'D' to 3, 'E' to 4, 'F' to 5, 'G' to 6, 'H' to 7, 'I' to 8, 'J' to 9)

    //Comprobamos si existe dentro del map y si es así devolvemos el caracter asignado.
    if (valores.containsKey(str))
    return valores[str]!!

    return 10 //Devolvemos 10 en caso de que no exista dentro del MAP.
}

//Obtiene una letra a traves de un numero
fun numALetra(num : Int) : Char
{
    if(num in 0..9)
    return Char(65 + num)

    return 'N'
}

//Genera las posiciones antes de comprobar si se superponen
fun generarPos(b : Barco, j: Jugador)
{

    var loop = true
    while(loop)
    {
        val array = ArrayList<Int>()
        val pos = (0..9).random() //Genera una posicion aleatoria para sumar o restar apartir de esta
        val tArray = Array(10) { Array(10) {0} }

        fun derechaIzquierda()
        {
            val lR = (1..2).random() //Dictamina la direccion : 1 - Izquierda | 2 - Derecha
            var acc = pos


            for(i in 0 until b.getTamanyo()) //Inferior a 0
            {
                if(acc < 0)
                {
                    val max = array.maxByOrNull { it }

                    if (max != null)
                        array.add(max + 1)

                }
                else if (acc > 9)
                {
                    val min = array.minByOrNull { it }

                    if (min != null)
                        array.add(min - 1)
                }
                else
                {
                    array.add(acc)
                }

                if (lR == 1)
                { acc -= 1 }
                else
                { acc += 1 }
            }
        }

        fun arribaAbajo()
        {
            val aA = (1..2).random()

            for(i in array)
            {
                if(aA == 1)
                {
                    tArray[pos][i] = 1
                }
                else
                {
                    tArray[i][pos] = 1
                }
            }
        }

        derechaIzquierda()
        arribaAbajo()

        loop = comprobarPos(tArray,ocupadas,b,j)
    }
}

//Comprueba la posicion y si es correcta la introduce en el array
fun comprobarPos(array1 : Array<Array<Int>>, array2 : Array<Array<Int>>, b : Barco, j : Jugador) : Boolean
{

    var tArray = Array(10) { Array(10) {0} }

    for (i in 0..9)
    {
        for (j in 0..9)
        {
            //Comprueba que el valor del array introducido no se encuentre en el
            //array de coordenadas ocupadas y además mira si su valor es 1 para que omita el valor 0
            if(array2[i][j].toString().contains(array1[i][j].toString()) && array2[i][j] == 1)
            {
                tArray = limpiarArray()
                return true
            }

            else if(array1[i][j] == 1)
            {
                tArray[i][j] = array1[i][j]
            }
        }
    }

    for(i in 0..9) // Establece los valores del tablero
    {
        for(j in 0..9)
        {
            if(array1[i][j] == 1)
            {
                ocupadas[i][j] = tArray[i][j]
            }
        }
    }


    val arr = ArrayList<String>() // Establece las coordenadas donde se situa cada barco
    for(i in 0..9) // Muestra cada posicion de cada barco
    {
        for (j in 0..9)
        {
            if(array1[i][j] == 1)
            {
                arr.add("${numALetra(i)}${j+1}")
            }
        }
    }
    val array = arrayOfNulls<String>(arr.size)
    b.setCoordenada(arr.toArray(array))

    j.setTablero(ocupadas)
    return false
}

//Limpia el array en caso de necesitarlo
fun limpiarArray(): Array<Array<Int>> {
    return Array(10) { Array(10) { 0 } }
}

//Genera barcos de forma automatica
fun genAutomatico(j1 : Jugador, j2 : Jugador)
{
    val bs1 = j1.getBarcos()
    bs1.add(Barco(2))
    bs1.add(Barco(3))
    bs1.add(Barco(3))
    bs1.add(Barco(4))
    bs1.add(Barco(5))

    j1.anyadirBarco(bs1)

    val bs2 = j2.getBarcos()
    bs2.add(Barco(2))
    bs2.add(Barco(3))
    bs2.add(Barco(3))
    bs2.add(Barco(4))
    bs2.add(Barco(5))

    j2.anyadirBarco(bs2)

    for (i in 0 until bs1.size)
    {
        generarPos(bs1[i],j1)
    }

    ocupadas = Array(10) { Array(10) {0} }

    for (i in 0 until bs2.size)
    {
        generarPos(bs2[i],j2)
    }
}

//El flujo de una partida normal
fun partida(j1 : Jugador,j2 : Jugador)
{
    while (true)
    {
        println("      Es tu turno Jugador 1.")
        mostartablero(j2)
        lanzarMisil(j2)
        if (j2.getBarcos().size == 0)
        {
            println("¡Enhorabuena Jugador 1 HAS GANADO!")
            break
        }
        println("      Es tu turno Jugador 2.")
        mostartablero(j1)
        lanzarMisil(j1)
        if (j1.getBarcos().size == 0)
        {
            println("¡Enhorabuena Jugador 2 HAS GANADO!")
            break
        }
    }
}

//El flujo de una partida contra un bot
fun partidaBot(j1 : Jugador,j2 : Jugador)
{
    while (true)
    {
        println("      Es tu turno Jugador 1.")
        mostartablero(j2)
        lanzarMisil(j2)

        if (j2.getBarcos().size == 0)
        {
            println("¡Enhorabuena Jugador 1 HAS GANADO!")
            break
        }
        println("        Es tu turno Bot.")

        Thread.sleep(2000)
        misilAutomatico(j1)

        if (j1.getBarcos().size == 0)
        {
            println("Lo sentimos, has perido!")
            break
        }
    }
}

//Funcion para comprobar si el misil lanzado a acertado
fun lanzarMisil(j : Jugador)
{
    val tab = j.getTablero()

    println("Introduce una coordenada")
    val coor = readLine()!!.uppercase(Locale.getDefault())

    var bomba = false
    val barcosRestantes = j.getBarcos()

    for (b in j.getBarcos())
    {
        val list =  b.getCoordenada().toMutableList()

        if(b.getCoordenada().contains(coor))
        {
            val pDanyada = b.getPartesDanyadas()
            pDanyada.add(coor)
            b.sePartesDanyadas(pDanyada)

            list.remove(coor)
            b.setCoordenada(list.toTypedArray())
            b.setTamanyo(b.getTamanyo()-1)
            bomba = true
        }
    }

    var bIndex = -1
    for (b in j.getBarcos())
    {
        if(b.getTamanyo() == 0)
        {
            bIndex = j.getBarcos().indexOf(b)
        }
    }

    if (bomba)
    {
        if(bIndex != -1)
        {
            for (i in 0..30)
            {
                println()
            }
            println("¡HUNDIDO!\n")
            j.anyadirBarco(barcosRestantes)

            for (p in j.getBarcos()[bIndex].getPartesDanyadas())
            {
                val x = letraANum(p[0])
                val y = p[1]

                for (v in 0..9) //Actualiza el tablero segun los datos
                {
                    for (h in 0..9)
                    {
                        if("$v,$h" == "$x,$y")
                        {
                            tab[v][h-1] = 3
                        }
                    }
                }
            }
            barcosRestantes.removeAt(bIndex)
            j.setTablero(tab)

        }
        else
        {
            for (v in 0..9) //Actualiza el tablero segun los datos
            {
                for (h in 0..9)
                {
                    if("${numALetra(v)}$h" == coor)
                    {
                        tab[v][h-1] = 2
                    }
                }
            }
            j.setTablero(tab)

            for (i in 0..30)
            {
                println()
            }
            println("\t\t\t¡Tocado!\n")
        }
    }
    else
    {
        for (i in 0..30)
        {
            println()
        }
        println("\t\t\t¡Agua!\n")
    }
}

//Funcion para que el bot haga un misil automatico
fun misilAutomatico(j : Jugador)
{
    val tab = j.getTablero()

    val x = numALetra((0..9).random())
    val y = (1..10).random()


    println("Introduce una coordenada")
    val coor = "$x$y"

    var bomba = false
    val barcosRestantes = j.getBarcos()

    for (b in j.getBarcos())
    {
        val list =  b.getCoordenada().toMutableList()

        if(b.getCoordenada().contains(coor))
        {
            val pDanyada = b.getPartesDanyadas()
            pDanyada.add(coor)
            b.sePartesDanyadas(pDanyada)

            list.remove(coor)
            b.setCoordenada(list.toTypedArray())
            b.setTamanyo(b.getTamanyo()-1)
            bomba = true
        }
    }

    var bIndex = -1
    for (b in j.getBarcos())
    {
        if(b.getTamanyo() == 0)
        {
            bIndex = j.getBarcos().indexOf(b)
        }
    }

    if (bomba)
    {
        if(bIndex != -1)
        {
            for (i in 0..30)
            {
                println()
            }
            println("¡HUNDIDO!\n")
            j.anyadirBarco(barcosRestantes)

            for (p in j.getBarcos().get(bIndex).getPartesDanyadas())
            {
                val x = letraANum(p[0])
                val y = p[1]

                for (v in 0..9) //Actualiza el tablero segun los datos
                {
                    for (h in 0..9)
                    {
                        if("$v,$h" == "$x,$y")
                        {
                            tab[v][h-1] = 3
                        }
                    }
                }
            }
            barcosRestantes.removeAt(bIndex)
            j.setTablero(tab)

        }
        else
        {
            for (v in 0..9) //Actualiza el tablero segun los datos
            {
                for (h in 0..9)
                {
                    if("${numALetra(v)}$h" == coor)
                    {
                        tab[v][h-1] = 2
                    }
                }
            }
            j.setTablero(tab)

            for (i in 0..30)
            {
                println()
            }
            println("\t\t\t¡Tocado!\n")
        }
    }
    else
    {
        for (i in 0..30)
        {
            println()
        }
        println("\t\t\t¡Agua!\n")
    }
}

fun main()
{
    var loop = true

    val j1 = Jugador()
    val j2 = Jugador()

    println("Bienvenido a hundir la flota, este juego ha sido dessarrollado por Mario Bellido Jiménez\n" +
            "Este es un port del clasico juego ya mencionado programado en Kotlin, este hecho para jugar\n" +
            "2 personas o jugar contra un bot, además para que no tengas que introducir coordenadas\n" +
            "estas se te crearan automaticamente, sea como sea, espero que lo disfrutes!\n" +
            "---------------------------------------------------------------------------------------------\n")

    while(loop)
    {
        try
        {
            println("Selecciona el tipo de partida a jugar:\n" +
                    "(0) : 1 vs bot\n" +
                    "(1) : 1 vs 1")

            when(readLine()!!.toInt())
            {
                0 ->
                {
                    genAutomatico(j1,j2)
                    partidaBot(j1,j2)
                    loop = false
                }
                1 ->
                {
                    genAutomatico(j1,j2)
                    partida(j1,j2)
                    loop = false
                }
                else -> throw NumberFormatException()
            }
        }
        catch (e : NumberFormatException)
        {
            println("Opcion incorrecta intentalo de nuevo.\n")
        }
    }
}