import java.io.BufferedReader
import java.io.File
import java.io.FileReader

class InputUtils {
    companion object {
        fun readData() {
            var buf: BufferedReader? = null
            try {
                buf = BufferedReader(FileReader(File("input")))
                var line: String? = buf.readLine();
                var lineNumber = 0
                var arrLine: Array<Int>
                while (line != null) {
                    if (lineNumber > 0 && lineNumber < 4) {
                        arrLine = InputData.asIs[lineNumber-1]
                        populateRow(arrLine, line)
                    }
                    if (lineNumber > 5 && lineNumber < 9) {
                        val arrLine = InputData.toBe[lineNumber - 6]
                        populateRow(arrLine, line)
                    }
                    if (lineNumber == 10) {
                        InputData.G = Integer.valueOf(line.substring(2))
                    }
                    if (lineNumber == 11) {
                        InputData.H = Integer.valueOf(line.substring(2))
                    }
                    lineNumber++
                    line = buf.readLine()
                }
            } finally {
                buf!!.close()
            }
        }

        private fun populateRow(row: Array<Int>, line: String) {
            var index = 0;
            for (number in line.split(" ")) {
                row[index] = Integer.valueOf(number)
                index++
            }
        }
    }


}