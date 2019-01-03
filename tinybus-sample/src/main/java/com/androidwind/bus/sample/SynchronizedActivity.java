package com.androidwind.bus.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class SynchronizedActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView console;
    private StringBuilder mStringBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synchronized);
        mStringBuilder = new StringBuilder();
        console = findViewById(R.id.tv_output);
        console.setMovementMethod(ScrollingMovementMethod.getInstance());
        //normal local
        Button btnNormalLocal = findViewById(R.id.btn_normal_local_variables);
        btnNormalLocal.setOnClickListener(this);
        //normal member
        Button btnNormalMember = findViewById(R.id.btn_normal_member_variables);
        btnNormalMember.setOnClickListener(this);
        //non-static method
        Button btnNonStaticMethod = findViewById(R.id.btn_non_static_method);
        btnNonStaticMethod.setOnClickListener(this);
        //non-static block
        Button btnNonStaticBlock = findViewById(R.id.btn_non_static_block);
        btnNonStaticBlock.setOnClickListener(this);
        //non-static with method and block
        Button btnNonStaticWithMethodAndBlock = findViewById(R.id.btn_non_static_with_method_and_block);
        btnNonStaticWithMethodAndBlock.setOnClickListener(this);
        //static
        Button btnStatic = findViewById(R.id.btn_static);
        btnStatic.setOnClickListener(this);
        //type1
        Button btnType1 = findViewById(R.id.btn_type1);
        btnType1.setOnClickListener(this);
        //type2
        Button btnType2 = findViewById(R.id.btn_type2);
        btnType2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mStringBuilder.delete(0, mStringBuilder.length());
        console.setText(mStringBuilder.toString());
        switch (v.getId()) {
            case R.id.btn_normal_local_variables:
                doWithNormalLocal();
                break;
            case R.id.btn_normal_member_variables:
                doWithNormalMember();
                break;
            case R.id.btn_non_static_method:
                doWithNonStaticMethod();
                break;
            case R.id.btn_non_static_block:
                doWithNonStaticBlock();
                break;
            case R.id.btn_non_static_with_method_and_block:
                doWithNonStaticWithMethodAndBlock();
                break;
            case R.id.btn_static:
                doWithStatic();
                break;
            case R.id.btn_type1:
                doWithType1();
                break;
            case R.id.btn_type2:
                doWithType2();
                break;
        }
    }

    //1==============================================================================================
    private void doWithNormalLocal() {
        final TestNormalLocal testNormal = new TestNormalLocal();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                testNormal.calculate();
            }
        };
        new Thread(runnable).start();
        new Thread(runnable).start();
    }

    class TestNormalLocal {
        private void calculate() {
            int result = 0;//local variables, whenever calculate method is called by any threads, the local variables will be initialized.
            for (int i = 0; i < 100; i++) {
                result++;
                System.out.println(Thread.currentThread().getName() + " : " + result);
                mStringBuilder.append(Thread.currentThread().getName() + " : " + result).append("\n");
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    console.setText(mStringBuilder.toString());
                }
            });
        }
    }
    //1==============================================================================================

    //2==============================================================================================
    private void doWithNormalMember() {
        final TestNormalMember testNormal = new TestNormalMember();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                testNormal.calculate();
            }
        };
        new Thread(runnable).start();
        new Thread(runnable).start();
    }

    class TestNormalMember {

        int result = 0;//member variables, it is stored as a part of object in the heap, and will be shared to ant threads.

        private void calculate() {
            for (int i = 0; i < 100; i++) {
                result++;
                System.out.println(Thread.currentThread().getName() + " : " + result);
                mStringBuilder.append(Thread.currentThread().getName() + " : " + result).append("\n");
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    console.setText(mStringBuilder.toString());
                }
            });
        }
    }
    //2==============================================================================================

    //3==============================================================================================
    private void doWithNonStaticMethod() {
        final TestSynchronizedWithNonStaticMethod testSynchronizedWithNonStaticMethod = new TestSynchronizedWithNonStaticMethod();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                testSynchronizedWithNonStaticMethod.calculate();
            }
        };
        new Thread(runnable).start();
        new Thread(runnable).start();
    }

    class TestSynchronizedWithNonStaticMethod {

        private synchronized void calculate() {// synchronized scope is the object

            int result = 0;

            for (int i = 0; i < 100; i++) {
                result++;
                System.out.println(Thread.currentThread().getName() + " : " + result);
                mStringBuilder.append(Thread.currentThread().getName() + " : " + result).append("\n");
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    console.setText(mStringBuilder.toString());
                }
            });
        }
    }
    //3==============================================================================================

    //4==============================================================================================
    private void doWithNonStaticBlock() {
        final TestSynchronizedWithNonStaticBlock testSynchronizedWithNonStaticBlock = new TestSynchronizedWithNonStaticBlock();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                testSynchronizedWithNonStaticBlock.calculate();
            }
        };
        new Thread(runnable).start();
        new Thread(runnable).start();
    }

    class TestSynchronizedWithNonStaticBlock {

        private void calculate() {
            // synchronized scope is the object's synchronized block
            synchronized (this) {
                int result = 0;
                for (int i = 0; i < 100; i++) {
                    result++;
                    System.out.println(Thread.currentThread().getName() + " : " + result);
                    mStringBuilder.append(Thread.currentThread().getName() + " : " + result).append("\n");
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        console.setText(mStringBuilder.toString());
                    }
                });
            }
        }
    }
    //4==============================================================================================

    //5==============================================================================================
    private void doWithNonStaticWithMethodAndBlock() {
        final TestSynchronizedWithNonStaticMethodAndBlock testSynchronizedWithNonStaticMethodAndBlock = new TestSynchronizedWithNonStaticMethodAndBlock();
        final Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                testSynchronizedWithNonStaticMethodAndBlock.calculate();
            }
        };
        final Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                testSynchronizedWithNonStaticMethodAndBlock.compute();
            }
        };
        final Runnable runnable3 = new Runnable() {
            @Override
            public void run() {
                testSynchronizedWithNonStaticMethodAndBlock.simulate();
            }
        };
//        new Thread(runnable1).start();
//        new Thread(runnable1).start();
        new Thread(runnable2).start();
        new Thread(runnable3).start();
    }

    class TestSynchronizedWithNonStaticMethodAndBlock {

        // synchronized scope is the object's synchronized block,
        // but if this object has any else synchronized block, they will always be blocked.
        // the non-synchronized block will not be effected.
        private void calculate() {
            int count = 0;
            for (int j = 0; j < 1000; j++) {
                count++;
                System.out.println(Thread.currentThread().getName() + " with count : " + count);
            }
            synchronized (this) {
                int result = 0;
                for (int i = 0; i < 1000; i++) {
                    result++;
                    System.out.println(Thread.currentThread().getName() + " : " + result);
                    mStringBuilder.append(Thread.currentThread().getName() + " : " + result).append("\n");
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        console.setText(mStringBuilder.toString());
                    }
                });
            }
        }

        private synchronized void compute() {
            int result = 0;
            for (int i = 0; i < 100; i++) {
                result++;
                System.out.println(Thread.currentThread().getName() + " with compute : " + result);
                mStringBuilder.append(Thread.currentThread().getName() + " : " + result).append("\n");
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    console.setText(mStringBuilder.toString());
                }
            });
        }

        private synchronized void simulate() {
            int result = 0;
            for (int i = 0; i < 100; i++) {
                result++;
                System.out.println(Thread.currentThread().getName() + " with simulate : " + result);
                mStringBuilder.append(Thread.currentThread().getName() + " : " + result).append("\n");
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    console.setText(mStringBuilder.toString());
                }
            });
        }
    }
    //5==============================================================================================

    //6==============================================================================================
    private void doWithStatic() {

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final String s = TestSynchronizedWithStatic.calculate(mStringBuilder);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        console.setText(s);
                    }
                });
            }
        };

        new Thread(runnable).start();
        new Thread(runnable).start();
    }

    //6==============================================================================================

    private void doWithType1() {
        TestRunnable.result = 0;
        final TestRunnable runnable = new TestRunnable();
        runnable.setBusCallBack(new TestRunnable.BusCallBack() {
            @Override
            public void getResult(final String content) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        console.setText(content);
                    }
                });
            }
        });
        runnable.setSB(mStringBuilder);
        new Thread(runnable).start();
        new Thread(runnable).start();
    }

    private void doWithType2() {
        TestRunnable.result = 0;
        final TestRunnable runnable1 = new TestRunnable();
        runnable1.setBusCallBack(new TestRunnable.BusCallBack() {
            @Override
            public void getResult(final String content) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        console.setText(content);
                    }
                });
            }
        });
        runnable1.setSB(mStringBuilder);

        final TestRunnable runnable2 = new TestRunnable();
        runnable2.setBusCallBack(new TestRunnable.BusCallBack() {
            @Override
            public void getResult(final String content) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        console.setText(content);
                    }
                });
            }
        });
        runnable2.setSB(mStringBuilder);

        new Thread(runnable1).start();
        new Thread(runnable2).start();
    }

}
