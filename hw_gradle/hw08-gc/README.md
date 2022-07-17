# GC investigation

## Results

| heap size |   msec / sec    | optimized  msec / sec | 
|-----------|:---------------:|:---------------------:|
| 256M      | `out of memory` |        764 / 0        |       no       |
| 512M      |   12716 / 12    |        767 / 0        |       no       |
| 768M      |   11318 / 11    |        763 / 0        |       no       |
| 1G        |   11394 / 11    |        764 / 0        |       no       |
| 2G        |   10762 / 10    |        765 / 0        |       no       |
| 4G        |   10653 / 10    |        767 / 0        |       no       |
| 8G        |   12582 / 12    |        771 / 0        |       no       |

the link to optimization changes [here](https://github.com/TohaVoice/2021-12-otus-java-professional-shatokhin/commit/a8bd9552a6ee0c2cbb4289b7495b81cddc515c78)
