package com.gabriel.diccionariowithtrie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_search.*

class Trie(words: Iterable<String>) : Iterable<String> {

    private val nodes = mutableListOf<TrieNode>()

    private fun addWord(nodes: MutableList<TrieNode>, word: String) {
        if (word.isEmpty() && nodes.none { it === EndNode }) {
            nodes.add(EndNode)
            return
        }

        val letter = word.first()
        val rest = word.drop(1)

        val child = nodes.find {
            when (it) {
                is ValueNode -> it.value == letter
                else -> false
            }
        }

        when (child) {
            is ValueNode -> addWord(child.nodes, rest)
            else -> {
                val nextNode = ValueNode(letter)
                addWord(nextNode.nodes, rest)
                nodes.add(nextNode)
            }
        }
    }

    private fun collectWords(nodes: List<TrieNode>, word: String): Sequence<String> {

        return sequence {
            val sortedNodes = nodes.sortedBy {
                when (it) {
                    is ValueNode -> it.value
                    is EndNode -> ' '
                    else -> null
                }
            }

            for (node in sortedNodes) {
                when (node) {
                    is ValueNode -> {
                        val nextWord = word + node.value
                        yieldAll(collectWords(node.nodes, nextWord))
                    }
                    is EndNode -> yield(word)
                }
            }
        }
    }

    infix fun has(word: String): Boolean {

        fun inNodes(nodes: List<TrieNode>, word: String): Boolean {
            if (word.isEmpty()) {
                return nodes.any { it === EndNode }
            }

            val firstLetter = word.first()

            val child = nodes.find {
                when (it) {
                    is ValueNode -> it.value == firstLetter
                    else -> false
                }
            }

            return when (child) {
                is ValueNode -> inNodes(child.nodes, word.drop(1))
                else -> false
            }
        }

        return inNodes(nodes, word)
    }

    override fun iterator(): Iterator<String> {
        return collectWords(nodes, "").iterator()
    }

    init {
        for (word in words) {
            addWord(nodes, word)
        }
    }
}

// Add to TrieNode.kt:
sealed class TrieNode
data class ValueNode(val value: Char): TrieNode() {
    val nodes = mutableListOf<TrieNode>()
}
object EndNode: TrieNode()

class Word {
    var texto: String
    var meaning: String
    var aymara: String
    var ingles: String

    constructor(texto: String, meaning: String,aymara: String, ingles: String) {
        this.texto = texto
        this.meaning = meaning
        this.aymara = aymara
        this.ingles = ingles
    }
}

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val words = listOf(
            "hello",
            "help",
            "helicopter",
            "hero",
            "hope",
            "echo",
            "hotel",
            "hot",
            "hop",
            "pablito",
            "bar",
            "chef",
            "dios",
            "fan",
            "fiel",
            "flor",
            "miel",
            "paz",
            "rey",
            "sol"

        )
        val trie = Trie(words)
        for (word in trie) {
            println(word)
        }
        setup(trie)
    }

    fun buscarSignificado(word: String): Word {
        val pablito = Word("pablito", "badani", "cunumi", "pablish")
        val bar = Word("bar" , "Establecimiento en el que hay un mostrador alargado para servir bebidas" , "Puri" , "Pub")
        val dios = Word("dios" , "En las religiones politeístas, ser sobrenatural al que se rinde culto ","Pacha Qama" , "God")
        val chef = Word("chef" , "Jefe de cocina de un restaurante u otro establecimiento donde se sirven comidas." , "Phayiri" , "Chef")
        val fan = Word("fan" , "Admirador o seguidor apasionado de una persona o cosa.","Arkiri" , "Fan")
        val fiel = Word("fiel" , "Que es firme y constante en sus afectos, ideas y obligaciones y cumple con sus compromisos hacia alguien o algo.", "Mayjaña" , "Faithful")
        val flor = Word("flor" , "Parte de las plantas. ","Panqara " , "Flower")
        val miel = Word("miel" , "Sustancia espesa, pegajosa y muy dulce que elaboran las abejas.","Misk´i" , "Honey")
        val paz = Word("paz" , "Situación o estado en que no hay guerra ni luchas entre dos o más partes enfrentadas.","Sumankaña" , "Peace")
        val rey = Word("rey" , "Soberano de una monarquía o un reino.","Mallku" , "King")
        val sol = Word("sol" , "Estrella con luz propia alrededor de la cual gira la Tierra.","Willka" , "Sun")

        val words = listOf<Word>(pablito, bar, chef, dios, fan, fiel,flor, miel, paz, rey, sol,)
        for(i in words.indices){
            if(words [i].texto.equals(word)){
                return words[i]
            }
        }
        return Word("", "", "", "")
    }
    private fun setup(trie: Trie) {
        title = "Search"

        buttonBuscador.setOnClickListener {
            if (buscador.text.isNotEmpty()) {
                if(trie.has(buscador.text.toString())){
                  val pal = buscarSignificado(buscador.text.toString())
                    textPalabraBuscada.setText(pal.meaning)
                    textAymara.setText(pal.aymara)
                    textIngles.setText(pal.ingles)
                    //buscador.setText(pal.meaning)

                }else{
                    val intent = Intent(this, NoResultsActivity::class.java)
                    startActivity(intent)
                    finish()
                    //buscador.setText("palabra no encontrada")
                }

            }
        }
    }
}




