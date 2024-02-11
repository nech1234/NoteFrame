package com.example.noteframe

import NoteRVAdapter
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noteframe.databinding.ActivityNoteBinding
import kotlin.math.abs

class NoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoteBinding
    private lateinit var adapter: NoteRVAdapter
    private lateinit var layoutManager: LinearLayoutManager

    private var currentPage = 1
    private var totalSize = 0 // 이미지 리스트의 크기를 저장하는 변수

    private val images = listOf(
        R.drawable.dice_1,
        R.drawable.dice_2,
        R.drawable.dice_3
        // Add more image resources as needed
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        totalSize = images.size // 이미지 리스트의 크기를 초기화

        adapter = NoteRVAdapter(images)
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        binding.notePaintRv.apply {
            setHasFixedSize(true)
            layoutManager = this@NoteActivity.layoutManager
            adapter = this@NoteActivity.adapter
        }

        // 초기 페이지 설정
        currentPage = savedInstanceState?.getInt("currentPage") ?: 1
        updatePageInfo()
        binding.noteMenuTotalPageInfoTv.text = totalSize.toString()

        // RecyclerView 스크롤 이벤트 처리
        val scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val position = (layoutManager.findFirstVisibleItemPosition() + layoutManager.findLastVisibleItemPosition()) / 2
                    currentPage = position + 1
                    updatePageInfo()
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                // 오른쪽으로 스와이프할 때
                if (dx > 0) {
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                    // 다음 이미지가 화면에 보일 때
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount) {
                        // 스와이프한 거리가 일정 기준(전체 화면의 20%) 이상인 경우에만 다음 페이지로 이동
                        if (recyclerView.computeHorizontalScrollOffset() >= recyclerView.computeHorizontalScrollRange() * 0.2f) {
                            if (currentPage < totalSize) {
                                currentPage++
                                updatePageInfo()
                                recyclerView.smoothScrollToPosition(currentPage - 1)
                            }
                        }
                    }
                }

                // 왼쪽으로 스와이프할 때
                else if (dx < 0) {
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                    // 이전 이미지가 화면에 보일 때
                    if (firstVisibleItemPosition == 0) {
                        // 스와이프한 거리가 일정 기준(전체 화면의 20%) 이상인 경우에만 이전 페이지로 이동
                        if (abs(recyclerView.computeHorizontalScrollOffset()) >= recyclerView.computeHorizontalScrollRange() * 0.2f) {
                            if (currentPage > 1) {
                                currentPage--
                                updatePageInfo()
                                recyclerView.smoothScrollToPosition(currentPage - 1)
                            }
                        }
                    }
                }
            }
        }

        // RecyclerView에 스와이프 리스너 연결
        binding.notePaintRv.addOnScrollListener(scrollListener)

        // 이전 페이지로 이동하는 버튼 클릭 이벤트 처리
        binding.noteMenuPrePageIv.setOnClickListener {
            previousPage()
        }

        // 다음 페이지로 이동하는 버튼 클릭 이벤트 처리
        binding.noteMenuNextPageIv.setOnClickListener {
            nextPage()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("currentPage", currentPage)
    }

    // 이전 페이지로 이동하는 메서드
    private fun previousPage() {
        if (currentPage == 1) {
            Toast.makeText(this, "첫 번째 페이지입니다", Toast.LENGTH_LONG).show()
        } else {
            currentPage--
            updatePageInfo()
            binding.notePaintRv.smoothScrollToPosition(currentPage - 1)
        }
    }

    // 다음 페이지로 이동하는 메서드
    private fun nextPage() {
        val nextPage = currentPage + 1
        if (nextPage > totalSize) {
            Toast.makeText(this, "마지막 페이지입니다", Toast.LENGTH_LONG).show()
        } else {
            currentPage = nextPage
            updatePageInfo()
            binding.notePaintRv.smoothScrollToPosition(currentPage - 1)
        }
    }



    // 페이지 정보를 업데이트하는 메서드
    private fun updatePageInfo() {
        binding.noteMenuCurPageInfoTv.text = currentPage.toString()
    }
}
