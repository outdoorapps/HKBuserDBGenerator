enum class Company { KMB, CTB, NLB, MTRB }

enum class Bound(val value: String) {
    I("I"),
    O("O");

    companion object {
        public fun fromValue(value: String): Bound = when (value) {
            "I" -> I
            "O" -> O
            else -> throw IllegalArgumentException()
        }
    }
}
