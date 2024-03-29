package com.na.dgsw.gongyou_android.presentation.ui.main.fragment

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.na.dgsw.gongyou_android.BR
import com.na.dgsw.gongyou_android.R
import com.na.dgsw.gongyou_android.presentation.ui.base.BaseFragment
import com.na.dgsw.gongyou_android.domain.entity.file.FileKind
import com.na.dgsw.gongyou_android.databinding.FragmentFileBinding
import com.na.dgsw.gongyou_android.presentation.ui.send.SendActivity
import com.na.dgsw.gongyou_android.presentation.ui.main.viewmodel.SendMainViewModel
import com.na.dgsw.gongyou_android.presentation.ui.main.recycler.OnItemClickListener
import com.na.dgsw.gongyou_android.presentation.ui.main.recycler.adapter.FileKindListAdapter
import com.vincent.filepicker.Constant
import com.vincent.filepicker.activity.AudioPickActivity
import com.vincent.filepicker.activity.ImagePickActivity
import com.vincent.filepicker.activity.NormalFilePickActivity
import com.vincent.filepicker.activity.VideoPickActivity
import com.vincent.filepicker.filter.entity.AudioFile
import com.vincent.filepicker.filter.entity.ImageFile
import com.vincent.filepicker.filter.entity.NormalFile
import com.vincent.filepicker.filter.entity.VideoFile
import java.lang.StringBuilder


/**
 * Created by NA on 2020-05-18
 * skehdgur8591@naver.com
 */

class SendMainFragment : BaseFragment<FragmentFileBinding, SendMainViewModel>() {

    override val viewModelClass: Class<SendMainViewModel>
        get() = SendMainViewModel::class.java

    // 더미 데이터를 뷰모델에서 처리하려 했으나 'context'를 가져와야 하여 프래그먼트에서 처리
    private val fileKindList = arrayListOf<FileKind>()

    private fun setFileKindListData(): ArrayList<FileKind> =
        fileKindList.apply {
            add(FileKind(getImageId("ic_image"), "이미지"))
            add(FileKind(getImageId("ic_video"), "비디오"))
            add(FileKind(getImageId("ic_audio"), "오디오"))
            add(FileKind(getImageId("ic_document"), "문서"))
        }

    private fun getImageId(imageName: String): Int {
        return requireContext().resources.getIdentifier("drawable/" + imageName, null, requireContext().packageName)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_file
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun setUp() {
        setRecyclerView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            Constant.REQUEST_CODE_PICK_IMAGE -> {
                if (resultCode == ImagePickActivity.RESULT_OK) {
                    val list = (data?.getParcelableArrayListExtra<ImageFile>(Constant.RESULT_PICK_IMAGE))
                    val builder = StringBuilder()
                    list?.let {
                        for (file in it) {
                            val path = file.path
                            builder.append(path + "\n")
                        }
                    }

                    val intent = setIntentWithDataUrl(builder.toString())
                    intent.putExtra("extension", list)
                    startActivity(intent)
                }
            }
            Constant.REQUEST_CODE_PICK_VIDEO -> {
                if (resultCode == VideoPickActivity.RESULT_OK) {
                    val list = (data?.getParcelableArrayListExtra<VideoFile>(Constant.RESULT_PICK_IMAGE))
                    val builder = StringBuilder()
                    list?.let {
                        for (file in it) {
                            val path = file.path
                            builder.append(path + "\n")
                        }
                    }

                    val intent = setIntentWithDataUrl(builder.toString())
                    startActivity(intent)
                }
            }
            Constant.REQUEST_CODE_PICK_AUDIO -> {
                if (resultCode == AudioPickActivity.RESULT_OK) {
                    val list = (data?.getParcelableArrayListExtra<AudioFile>(Constant.RESULT_PICK_IMAGE))
                    val builder = StringBuilder()
                    list?.let {
                        for (file in it) {
                            val path = file.path
                            builder.append(path + "\n")
                        }
                    }

                    val intent = setIntentWithDataUrl(builder.toString())
                    startActivity(intent)
                }
            }
            Constant.REQUEST_CODE_PICK_FILE -> {
                if (resultCode == NormalFilePickActivity.RESULT_OK) {
                    val list = (data?.getParcelableArrayListExtra<NormalFile>(Constant.RESULT_PICK_IMAGE))
                    val builder = StringBuilder()
                    list?.let {
                        for (file in it) {
                            val path = file.path
                            builder.append(path + "\n")
                        }
                    }

                    val intent = setIntentWithDataUrl(builder.toString())
                    startActivity(intent)
                }
            }
        }
    }

    override fun observerViewModel() {
        with(viewModel) {

        }
    }

    private fun setRecyclerView() {
        val fileKindListAdapter = FileKindListAdapter(setFileKindListData())
        recyclerViewSetClickListener(fileKindListAdapter)

        binding.recyclerview.adapter = fileKindListAdapter
    }

    private fun recyclerViewSetClickListener(fileKindListAdapter: FileKindListAdapter) {
        val onItemClickListener: OnItemClickListener = object : OnItemClickListener {
            override fun onItemClick(v: View, position: Int) {
                when (position) {
                    0 -> {
                        val intent = Intent(requireActivity().application, ImagePickActivity::class.java)
                        intent.putExtra(ImagePickActivity.IS_NEED_CAMERA, true)
                        intent.putExtra(Constant.MAX_NUMBER, 9)
                        startActivityForResult(intent, Constant.REQUEST_CODE_PICK_IMAGE)
                    }
                    1 -> {
                        val intent = Intent(requireActivity().application, VideoPickActivity::class.java)
                        intent.putExtra(VideoPickActivity.IS_NEED_CAMERA, true)
                        intent.putExtra(Constant.MAX_NUMBER, 9)
                        startActivityForResult(intent, Constant.REQUEST_CODE_PICK_VIDEO)
                    }
                    2 -> {
                        val intent = Intent(requireActivity().application, AudioPickActivity::class.java)
                        intent.putExtra(AudioPickActivity.IS_NEED_RECORDER, true)
                        intent.putExtra(Constant.MAX_NUMBER, 9)
                        startActivityForResult(intent, Constant.REQUEST_CODE_PICK_AUDIO)
                    }
                    3 -> {
                        val intent = Intent(requireActivity().application, NormalFilePickActivity::class.java)
                        intent.putExtra(NormalFilePickActivity.SUFFIX, arrayOf("xlsx", "xls", "doc", "docx", "ppt", "pptx", "pdf"))
                        intent.putExtra(Constant.MAX_NUMBER, 9)
                        startActivityForResult(intent, Constant.REQUEST_CODE_PICK_FILE)
                    }
                }
            }
        }

        fileKindListAdapter.setOnItemClickListener(onItemClickListener)
    }

    private fun setIntentWithDataUrl(dataUrl: String): Intent =
        Intent(activity, SendActivity::class.java).apply {
            putExtra("dataUrl", dataUrl)
        }

}