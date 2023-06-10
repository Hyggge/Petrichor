'''
Copyright (C) 2022 BUAA
Author: Hyggge, <hyggge6@gmail.com>

该程序应该在项目根目录下运行
'''

import os
import sys
import shutil

MAS_JAR_PATH = './script/Mars.jar'
MIPS_CODE_PATH = './mips.txt'
COMPILER_PARAM = '-geo'
CACHE_FILE = './script/test_info/cache.txt'

TESTFILE_PATH = './testfile.txt'
INPUT_PATH = './input.txt'
MODIFIED_TESTGILE_PATH = './script/test_info/modified_testfile.c'
MODIFIED_INPUT_PATH = './script/test_info/modified_input.txt'
MARS_OUT_PATH = './script/test_info/mars_out.txt'
# GCC_EXCU_PATH = './script/test_info/main.out' # linux
GCC_EXCU_PATH = '.\script\\test_info\\main.exe' # windows
GCC_OUT_PATH = './script/test_info/gcc_out.txt'
LOG_PATH = './script/test_info/log.txt'

class ColorfulPrint():
    MODE_NORMAL = 1
    MODE_BOLD = 2
    COLOR_GRAY = 0
    COLOR_RED = 1
    COLOR_GREEN = 2
    COLOR_YELLO = 3
    COLOR_BLUE = 4
    COLOR_PINK = 5
    COLOR_CYAN = 6
    COLOR_WHITE = 7
    @staticmethod
    def colorfulPrint(msg, mode, color):
        head = '\033['
        if mode == ColorfulPrint.MODE_NORMAL:
            head = head + '0;'
        elif mode == ColorfulPrint.MODE_BOLD:
            head = head + '1;'
        else:
            print('\033[1;31;40m' + ' ***** MODE ERROR ***** ' + '\033[0m')
            return
        if 0 <= color <= 7:
            head = head + str(30 + color) + ';40m'
        else:
            print('\033[1;31;40m' + ' ***** COLOR ERROR ***** ' + '\033[0m')
        tail = '\033[0m'
        print(head + msg + tail)


def make_jar():
    os.system('javac -d ./out -cp ./src/ -encoding UTF-8 ./src/Compiler.java')
    os.chdir('./out')
    os.system('jar cmvf ../src/META-INF/MANIFEST.MF Compiler.jar .')
    os.chdir('../')
    shutil.copyfile('./out/Compiler.jar', './Compiler.jar')


def modify_input():
    with open(INPUT_PATH, 'r', encoding='utf-8') as f:
        inputData = f.readlines()
    res = []
    for line in inputData:
        if (line.strip() == ''): continue
        res = res + list(map(lambda x: str(x) + '\n', list(map(int, line.split(' ')))))
    with open(MODIFIED_INPUT_PATH, 'w', encoding='utf-8') as f:
        inputData = f.writelines(res)


def modify_testfile():
    with open(TESTFILE_PATH, 'r', encoding='utf-8') as f:
        ori_testfile_content = f.readlines()
    with open(MODIFIED_TESTGILE_PATH, 'w', encoding='utf-8') as f:
        getint_def = \
        '''
        #include <stdio.h>
        #include <stdlib.h>
        int getint() {
            int input;
            scanf("%d", &input);
            return input;
        }
        '''
        f.write(getint_def)
        f.writelines(ori_testfile_content)



def run():
    modify_input()
    modify_testfile()
    # 运行Java项目，生成MIPS代码
    os.system(f'java -jar Compiler.jar {COMPILER_PARAM} > {CACHE_FILE}')
    # Mars运行MIPS代码，结果输出到mars_out.txt中
    os.system(f"java -jar {MAS_JAR_PATH}  nc {MIPS_CODE_PATH} < {MODIFIED_INPUT_PATH} > {MARS_OUT_PATH}")
    # gcc运行源代码,结果输出到gcc_out.txt
    os.system(f'gcc {MODIFIED_TESTGILE_PATH} -o {GCC_EXCU_PATH}')
    os.system(f'{GCC_EXCU_PATH} < {MODIFIED_INPUT_PATH} > {GCC_OUT_PATH}')
        

def cmp_file():
    res = True
    with open(MARS_OUT_PATH, 'r', encoding='utf-8') as f:
        mars_out = f.readlines()
    with open(GCC_OUT_PATH, 'r', encoding='utf-8') as f:
        gcc_out = f.readlines()
    with open(LOG_PATH, 'w', encoding='utf-8') as log:
        if (len(mars_out) != len(gcc_out)):
            log.write('行数不一致！\n\n')
            res = False
        else:
            for i in range(len(mars_out)):
                if (mars_out[i] != gcc_out[i]):
                    log.write(f'Error in line {i+1}: \nExpected output is \"{gcc_out[i]}\", but your outout is \"{mars_out[i]}\"\n\n')
                    res = False
    return res


def once_test():
    print('>>> 开始对项目进行打包...')
    make_jar()
    print('>>> 运行项目, 获得MIPS和GCC的运行结果...')
    run()
    print('>>> 对运行结果进行比较...')
    res = cmp_file()
    if res:
        ColorfulPrint.colorfulPrint('输出正确！\n', ColorfulPrint.MODE_BOLD, ColorfulPrint.COLOR_GREEN)
    else:
        ColorfulPrint.colorfulPrint('输出错误！\n', ColorfulPrint.MODE_BOLD, ColorfulPrint.COLOR_RED)


def multi_test():
    print('>>> 开始对项目进行打包...')
    make_jar()
    for year in ('2022', '2021'):
        for type in ('A', 'B', 'C'):
            path = f'./script/{year}/{type}'
            file_list = os.listdir(path)
            testfile_num = int(len(file_list) / 3)
            for i in range(0, testfile_num):
                testfile_name = path + f'/testfile{i + 1}.txt'
                input_name = path + f'/input{i + 1}.txt'
                shutil.copyfile(testfile_name, TESTFILE_PATH)
                shutil.copyfile(input_name, INPUT_PATH)
                
                print(f'>>> 正在测试 {testfile_name}....')
                print('>>> 运行项目, 获得MIPS和GCC的运行结果...')
                run()
                print('>>> 对运行结果进行比较...')
                res = cmp_file()
                if res:
                    ColorfulPrint.colorfulPrint('输出正确！\n', ColorfulPrint.MODE_BOLD, ColorfulPrint.COLOR_GREEN)
                else:
                    ColorfulPrint.colorfulPrint('输出错误！\n', ColorfulPrint.MODE_BOLD, ColorfulPrint.COLOR_RED)

    # year = '2022'
    # type = 'B'
    # no = '11'
    # testfile_name = f'./script/{year}/{type}/testfile{no}.txt'
    # input_name = f'./script/{year}/{type}/input{no}.txt'
    # shutil.copyfile(testfile_name, TESTFILE_PATH)
    # shutil.copyfile(input_name, INPUT_PATH)
    # modify_input(INPUT_PATH)
    # print(f'>>> 正在测试 {testfile_name}....')
    # print('>>> 运行项目, 获得MIPS和GCC的运行结果...')
    # run()
    # print('>>> 对运行结果进行比较...')
    # res = cmp_file()
    # if res:
    #     ColorfulPrint.colorfulPrint('输出正确！\n', ColorfulPrint.MODE_BOLD, ColorfulPrint.COLOR_GREEN)
    # else:
    #     ColorfulPrint.colorfulPrint('输出错误！\n', ColorfulPrint.MODE_BOLD, ColorfulPrint.COLOR_RED)


if __name__ == '__main__':
    # once_test()
    multi_test()