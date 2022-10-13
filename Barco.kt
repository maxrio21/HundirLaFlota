import java.util.*

class Barco(private var tamanyo: Int) {
    private var coordenada = Array(2) {""}
    private var partesDanyadas = ArrayList<String>()

    fun setTamanyo(t : Int) {this.tamanyo = t}
    fun getTamanyo() : Int { return this.tamanyo }

    fun setCoordenada(array : Array<String>) { this.coordenada = array }
    fun getCoordenada() : Array<String> { return this.coordenada }
    fun sePartesDanyadas(array : ArrayList<String>) { this.partesDanyadas = array }
    fun getPartesDanyadas() : ArrayList<String> { return this.partesDanyadas }
}