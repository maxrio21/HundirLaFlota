class Jugador
{
    private var tablero = Array(10) { Array(10) {0} }
    private var barcos = ArrayList<Barco>()

    fun getTablero() : Array<Array<Int>> { return this.tablero }
    fun getBarcos() : ArrayList<Barco> { return this.barcos }

    fun setTablero(tablero : Array<Array<Int>>)
    {
        this.tablero = tablero
    }

    fun anyadirBarco(barcos : ArrayList<Barco>)
    {
        this.barcos = barcos
    }
}
